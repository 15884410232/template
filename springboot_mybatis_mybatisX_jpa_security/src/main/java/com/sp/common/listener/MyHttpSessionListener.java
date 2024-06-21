package com.sp.common.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 监听session的创建销毁
 * @author chenqi
 * @date 2022/3/20
 */
@Slf4j
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.debug("监听器:MyHttpSessionListener 事件:session:sessionId:"+se.getSession().getId()+"创建");
        HttpSessionListener.super.sessionCreated(se);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug("监听器:MyHttpSessionListener 事件:session:sessionId:"+se.getSession().getId()+"销毁");
        HttpSessionListener.super.sessionDestroyed(se);
    }
}
