package com.sp.common.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 监听ServletRequest，访问服务器任何资源都会发送请求(ServletRequest)出现,访问.html和.jsp和.servlet都会创建请求
 * @author chenqi
 * @date 2022/3/20
 */
@Slf4j
@WebListener
public class MyServletRequestListener implements ServletRequestListener {

    /**
     * 访问服务器任何资源都会发送请求(ServletRequest)出现,访问.html和.jsp和.servlet都会创建请求
     * @param sre
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request=(HttpServletRequest)sre.getServletRequest();
        log.debug("监听器:MyServletRequestListener 事件:sessionId:"+request.getSession().getId()+"Request" +
                "创建");
        ServletRequestListener.super.requestInitialized(sre);
    }

    /**
     * 每次请求执行完成都会触发此方法
     * @param sre
     */
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request=(HttpServletRequest)sre.getServletRequest();
        log.debug("监听器:MyServletRequestListener 事件:sessionId:"+request.getSession().getId()+"Request销毁");
        ServletRequestListener.super.requestDestroyed(sre);
    }
}
