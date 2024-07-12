package com.sp.config.mvc;

import com.sp.common.filter.MyTestFilter;
import com.sp.common.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * 因为filter的初始化在spring之前，filter不归spring容器管理，向filter中注入bean将会是null,所以需要使用DelegatingFilterProxy
 */
@Configuration
public class FilterConfig implements WebMvcConfigurer {

    //必要bean ，用于创建Filter
    @Bean
    public Filter requestFilter() {
        return new RequestFilter();
    }

    @Bean
    public Filter myTestFilter() {
        return new MyTestFilter();
    }

    @Bean
    public FilterRegistrationBean requestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //设置filter
        registration.setFilter(new DelegatingFilterProxy("requestFilter"));
        registration.addUrlPatterns("/*");
        registration.setName("requestFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean myTestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //设置filter
        registration.setFilter(new DelegatingFilterProxy("myTestFilter"));
        registration.addUrlPatterns("/*");
        registration.setName("myTestFilter");
        registration.setOrder(1);
        return registration;
    }




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

//    @Bean
//    public FilterRegistrationBean<RequestFilter> requestFilterRegistration() {
//        FilterRegistrationBean<RequestFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new RequestFilter());
//
//
//        // 添加拦截的URL模式
//        registration.addUrlPatterns("/test/getUserList");
//
//        // 设置过滤器的执行顺序，数值越小，优先级越高
//        registration.setOrder(1);
//
//        // 返回配置好的FilterRegistrationBean
//        return registration;
//    }
}