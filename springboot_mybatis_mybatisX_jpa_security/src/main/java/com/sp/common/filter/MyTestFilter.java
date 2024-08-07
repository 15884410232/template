package com.sp.common.filter;

import com.sp.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * @author chenqi
 * @date 2022/3/19
 */
@Slf4j
//在security项目中。直接使用webFilter会失效，需要使用FilterRegistrationBean
//@WebFilter(urlPatterns = "/*")
//@Component
public class MyTestFilter implements Filter {
    @Resource
    private JedisPool jedisPool;

    @Resource
    private UserMapper userMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("MyTestFilter初始化");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("MyTestFilter执行");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        log.debug("MyTestFilter销毁");
        Filter.super.destroy();
    }
}
