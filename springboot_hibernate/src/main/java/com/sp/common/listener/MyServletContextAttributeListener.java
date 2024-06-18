package com.sp.common.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * 监听ServletContext属性变化
 * @author chenqi
 * @date 2022/3/20
 */
@Slf4j
@WebListener
public class MyServletContextAttributeListener implements ServletContextAttributeListener {

    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        ServletContextAttributeListener.super.attributeAdded(scae);
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        ServletContextAttributeListener.super.attributeRemoved(scae);
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        ServletContextAttributeListener.super.attributeReplaced(scae);
    }
}
