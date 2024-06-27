package com.sp.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // 认证成功后的逻辑处理
        // 例如，重定向到主页
//        response.sendRedirect("/home");

        // 或者返回JSON响应
        // response.setContentType("application/json");
        // response.getWriter().write("{\"message\": \"登录成功\"}");
    }
}