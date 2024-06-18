package com.sp.common.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * 监听HttpSession属性的改变
 * @author chenqi
 * @date 2022/3/20
 */
@Slf4j
@WebListener
public class MyHttpSessionAttributeListener implements HttpSessionAttributeListener {

    /**
     * 增加属性
     * @param se
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        HttpSessionAttributeListener.super.attributeAdded(se);
    }

    /**
     * 移除属性
     * @param se
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        HttpSessionAttributeListener.super.attributeRemoved(se);
    }

    /**
     * 属性替换
     * @param se
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        HttpSessionAttributeListener.super.attributeReplaced(se);
    }
}
