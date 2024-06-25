package com.sp.common.listener;

import com.sp.common.util.ApplicationContextUtil;
import com.sp.service.AppInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听容器启动-容器启动后做一些预处理操作
 *
 * @author chenqi
 * @version 1.0
 * @createDate 2022/03/24 16:42
 * @see com.sp.common.listener
 */
@Slf4j
@Component
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {


    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (log.isInfoEnabled()) {
            log.info("容器启动成功");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("JVM停止事件");

        }));
        AppInitService appInitService = ApplicationContextUtil.getBean(AppInitService.class);
        //初始化角色
        appInitService.initRole();
        //初始化权限根节点
        appInitService.initPermission();
    }
}
