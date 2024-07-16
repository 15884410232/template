package com.sp.common.filter;

import com.sp.common.comstant.CommonConstants;
import com.sp.common.util.CommonUtils;
import com.sp.common.util.Sha256Util;
import com.sp.common.util.StringUtils;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.logging.log4j.util.Base64Util;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author chenqi
 * @date 2022/3/19
 */
@Slf4j
//在security项目中。直接使用webFilter会失效，需要使用FilterRegistrationBean
//@WebFilter(urlPatterns = "/*")
//@Component
public class RequestFilter implements Filter {


    @Resource
    private JedisPool jedisPool;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("RequestFilter初始化");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

            RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);


            String params = "";
            String meth = httpServletRequest.getMethod();
            // 获取请求参数    params = httpServletRequest.getQueryString() == null ? "" : httpServletRequest.getQueryString();
            if (!"GET".equals(meth)) {
                params = params + CommonUtils.getMD5(StringUtil.isNullOrEmpty(requestWrapper.getBody()) ? "{}" : requestWrapper.getBody());
            }
            String paramStr = meth.toLowerCase() + "/" + params;
            // 接口签名，压测注释了签名，真实环境需要放开    if (
            if (!skipCheckSign(httpServletRequest.getRequestURI())&&!apiSignature(httpServletRequest, requestWrapper.getBody(), paramStr)) {
                throw new AuthenticationException("签名错误");
//                return;
            }


            filterChain.doFilter(requestWrapper, servletResponse);

        }

        @Override
        public void destroy () {
            log.debug("RequestFilter销毁");
            Filter.super.destroy();
        }


        private boolean apiSignature(HttpServletRequest httpServletRequest, String requestBody, String params){

            String token = httpServletRequest.getHeader("Authorization");
            try (Jedis jedis = jedisPool.getResource()) {
                String signature = httpServletRequest.getHeader("signature");
                signature = StringUtils.isBlank(signature) ? "" : signature;
                val signArray = signature.split("&");
                String sign = null;
                String uuid = null;
                String timeSpan = null;
                String key = null;

                // 获取参数
                for (int i = 0; i < signArray.length; i++) {
                    String s = signArray[i];
                    switch (i) {
                        case 0:
                            sign = s.substring(s.indexOf("=") + 1);
                            break;
                        case 1:
                            uuid = s.split("=")[1];
                            break;
                        case 2:
                            timeSpan = s.split("=")[1];
                            break;
                        case 3:
                            key = s.split("=")[1];
                            break;
                        default:
                            break;
                    }
                }

                if (
                        StringUtil.isNullOrEmpty(sign) ||
                                StringUtil.isNullOrEmpty(uuid) ||
                                StringUtil.isNullOrEmpty(timeSpan) ||
                                StringUtil.isNullOrEmpty(key)
                ) {
                    log.error(httpServletRequest.getRequestURI() + "签名参数错误:" + signature);
                    return false;
                }

                Long now = System.currentTimeMillis();
                // 三分钟前的请求直接挂掉
                if (now - Long.parseLong(timeSpan) > 1000 * 60 * 10) {
                    log.error(httpServletRequest.getRequestURI() + "签名超时:" + signature);
                    return false;
                }
                // 存在uuid，判定重放
                if (jedis.setnx(uuid, "") == 0) {
                    log.error(httpServletRequest.getRequestURI() + "签名重放:" + signature);
                    return false;
                }
                // uuid五分钟过期
                jedis.expire(uuid, 60 * 5);



                // 构造签名str
                if (params != null) {
                    params = URLDecoder.decode(params);
                }

                String signToStr = "url=" + httpServletRequest.getRequestURI() + "&param=" + params + "&uuid=" + uuid + "&timeSpan=" + timeSpan + "&key=" + key;

                String sig = Sha256Util.sha256_HMAC(signToStr, token);
                log.info(token);
                log.info(sig);
                String bSign = Base64Util.encode(Sha256Util.sha256_HMAC(signToStr, token));


                // 签名不一致
                if (!bSign.equals(sign)) {
                    log.error(httpServletRequest.getRequestURI() + "签名不一致:\n" + signature + "server:" + signToStr + "\n");
                    return false;
                }

            } catch (Exception ex) {
                log.error(httpServletRequest.getRequestURI() + "签名方法异常:" + ex);
                return false;
            }
            return true;
        }



        private boolean skipCheckSign(String url) {

            for (String u : CommonConstants.ApiUrl.SKIP_SIGN) {
                if (u.equals(url)) {
                    return true;
                }
            }
            return false;
        }


}
