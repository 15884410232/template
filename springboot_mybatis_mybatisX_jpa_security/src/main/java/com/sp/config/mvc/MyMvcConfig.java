package com.sp.config.mvc;

import com.sp.common.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 实现WebMvcConfigurer接口可以来扩展SpringMVC的功能
 * @author chenqi
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置拦截器的拦截路径，即除了/index.html、/、/loginPage都会被次拦截器拦截
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/index.html", "/", "/loginPage");
    }

    /**
     * 配置静态文件映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //表示文件路径，这里的意思是/webjars/下的所有文件，所有/webjars/开头的请求 都会去后面配置的路径下查找资源
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

//    @Bean
//    public FilterRegistrationBean<MyCharacterEncodingFilter> myCharacterEncodingFilter() {
//        FilterRegistrationBean<MyCharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new MyCharacterEncodingFilter());
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }
}
