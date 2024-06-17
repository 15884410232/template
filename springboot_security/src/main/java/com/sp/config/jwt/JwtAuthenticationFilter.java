package com.sp.config.jwt;

import com.sp.common.util.JwtUitl;
import com.sp.common.util.RedisUtil;
import com.sp.model.dto.req.LoginUser;
import com.sp.model.entity.sys.UserEntity;
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
    private JwtUitl jwtUitl;

    @Resource
    private JedisPool jedisPool;

    @Resource
    private RedisUtil redisUtil;
     

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("token");
        if(!StringUtils.hasText(token)){
            //如果token为空，则直接放行，因为在后续的过滤器链中，还有security相关的认证过滤器会校验是否登录，所以可以直接放行
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return ;
        }
        //解析token
        JwtUser jwtUser = jwtUitl.parseToken(token);
        //从redis获取用户信息
        LoginUser user = (LoginUser)redisUtil.get(token);
        //判断获取到的用户信息是否为空，因为redis里面可能并不存在这个用户信息，例如缓存过期了
        if(Objects.isNull(user)){
            //抛出一个异常
            throw new RuntimeException("用户未登录");
        }
        //把最终的LoginUser用户信息，通过setAuthentication方法，存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                //第一个参数是LoginUser用户信息，第二个参数是凭证(null)，第三个参数是权限信息(null)
                new UsernamePasswordAuthenticationToken(user,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //全部做完之后，就放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
