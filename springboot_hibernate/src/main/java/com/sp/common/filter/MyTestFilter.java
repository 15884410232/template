package com.sp.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author chenqi
 * @date 2022/3/19
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
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
