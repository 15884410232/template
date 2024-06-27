package com.sp.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author chenqi
 * @date 2022/3/19
 */
@Slf4j
//在security项目中。直接使用webFilter会失效，需要使用FilterRegistrationBean，指定顺序才会生效。自定义过滤器一定要指定顺序，否则失效
//@WebFilter(urlPatterns = "/*")
@Component
public class MyTestFilter implements Filter {
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
