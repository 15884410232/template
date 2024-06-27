package com.sp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.common.enums.MyUrlEnum;
import com.sp.common.filter.MyCharacterEncodingFilter;
import com.sp.config.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
//启用这个注解才能在资源上加上权限校验注解，@PreAuthorize
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Resource
    private MyCharacterEncodingFilter myCharacterEncodingFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 或者其他的PasswordEncoder实现
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //前后端分离不存在csrf攻击，所以关闭
        http.cors(withDefaults())
                // 禁用 CSRF
                .csrf().disable()
                // JWT的模式不再使用session，不通过session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 指定的接口直接放行
                .antMatchers(MyUrlEnum.login.getPath()).permitAll()
                // 其他的接口都需要认证后才能请求
                .anyRequest().authenticated();
//        http.formLogin().loginPage("/account/login").permitAll().failureHandler(customAuthenticationFailureHandler());
        //登录认证失败处理器
//        http.formLogin().failureHandler(customAuthenticationFailureHandler());
//        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);// 设置权限不足处理器
        //当用户未登录访问需要权限时，返回401
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint(new ObjectMapper()));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(myCharacterEncodingFilter, UsernamePasswordAuthenticationFilter.class);

    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
