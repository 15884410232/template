package com.sp.config.mvc;

import com.sp.common.filter.MyTestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyTestFilter> customFilterRegistration() {
        FilterRegistrationBean<MyTestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MyTestFilter());

        // 添加拦截的URL模式
        registration.addUrlPatterns("/test/getUserList");

        // 设置过滤器的执行顺序，数值越小，优先级越高
        registration.setOrder(1);

        // 返回配置好的FilterRegistrationBean
        return registration;
    }
}