package com.sp.config.security;

import com.alibaba.fastjson.JSON;
import com.sp.model.dto.response.base.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 记录鉴权失败日志
        System.out.println("鉴权失败：" + accessDeniedException.getMessage());

        // 设置响应状态码，例如403 Forbidden
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Result result=new Result();
        result.setCode(HttpServletResponse.SC_FORBIDDEN);
        result.setMessage(accessDeniedException.getMessage());

        // 返回给前端的JSON响应，根据实际情况调整
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(result));


        // 或者重定向到无权限提示页面
        // response.sendRedirect("/access-denied");
    }
}