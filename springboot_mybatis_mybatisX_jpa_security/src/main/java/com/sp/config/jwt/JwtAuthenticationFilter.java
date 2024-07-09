package com.sp.config.jwt;

import com.sp.common.comstant.CommonConstants;
import com.sp.common.enums.ResultCodeEnum;
import com.sp.common.util.JwtUitl;
import com.sp.common.util.RedisUtil;
import com.sp.common.util.TimeUtil;
import com.sp.config.security.MyUserDetails;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 因为jwt认证一次请求只需要经过一边过滤器，所以继承OncePerRequestFilter而不是webFilter
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtConfig jwtConfig;

    @Resource
    private JwtUitl jwtUitl;

    @Resource
    private JedisPool jedisPool;

    @Resource
    private RedisUtil redisUtil;
     

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("Authorization");
        //如果是不需要登录的接口直接放行
        if(passUrl(httpServletRequest.getRequestURI())){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return ;

        }
        //如果是其他接口，需要校验token
        if(!StringUtils.hasText(token)){
            //没有传递token,提示未登录
            throw new AuthenticationCredentialsNotFoundException(ResultCodeEnum.USER_NOT_LOGIN.getMessage());
        }
        //解析token
        JwtUser jwtUser = jwtUitl.parseToken(token);
        //从redis获取用户信息
        MyUserDetails user = (MyUserDetails)redisUtil.get("login_"+jwtUser.getUserId());
        //判断获取到的用户信息是否为空，因为redis里面可能并不存在这个用户信息，例如缓存过期了
        if(Objects.isNull(user)){
            //用户请求的token和redis中存的token不一致，则用户多地登录被挤掉了，提示重新登录
            throw new AuthenticationCredentialsNotFoundException(ResultCodeEnum.USER_NOT_LOGIN.getMessage());
        }
        String redisToken = (String) redisUtil.get("token_"+jwtUser.getUserId());
        if(!token.equals(redisToken)){
            //用户请求的token和redis中存的token不一致，则用户多地登录被挤掉了，提示重新登录
            throw new AccountExpiredException(ResultCodeEnum.USER_EXPIRE.getMessage());
        }
        Long maxIdle = (Long)redisUtil.get("maxIdel_" + jwtUser.getUserId());
        long now = TimeUtil.getUnixTime();
        if(now>maxIdle){
            //用户登录超时，提示重新登录
            throw new AccountExpiredException(ResultCodeEnum.USER_TIME_OUT.getMessage());
        }
        //更新redis中的token最大空闲时间，延续会话
        redisUtil.set("maxIdel_"+jwtUser.getUserId(),now+jwtConfig.getMaxIdleSecond(),now+jwtConfig.getMaxIdleSecond());

        //把最终的LoginUser用户信息，通过setAuthentication方法，存入SecurityContextHolder
        //第一个参数是LoginUser用户信息，第二个参数是凭证(null)，第三个参数是权限信息(null)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        //spring security就是根据authentication判断用户是否登录的
//        authenticationToken.setAuthenticated(false);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //全部做完之后，就放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    boolean passUrl(String url){
        for (String passUrl : CommonConstants.passUrl) {
            if(passUrl.endsWith("/**")){
                int index =passUrl.lastIndexOf("/");
                String prefix=passUrl.substring(0,index);
                if(url.contains(prefix)){
                    return true;
                }
            }
            if(url.contains(passUrl)){
                return true;
            }
        }
        return false;
    }
}
