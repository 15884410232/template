package com.sp.common.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 监听ServletContext域对象的创建与销毁：实现ServletContextListener接口。
 * @author chenqi
 * @date 2022/3/20
 */
@Slf4j
@WebListener
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.debug("监听器:MyServletContextListener 事件:servletContext启动");
        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.debug("监听器:MyServletContextListener 事件:servletContext销毁");
        ServletContextListener.super.contextDestroyed(sce);
    }
}
