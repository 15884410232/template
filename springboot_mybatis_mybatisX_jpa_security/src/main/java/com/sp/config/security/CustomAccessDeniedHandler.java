package com.sp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.common.enums.ResultCode;
import com.sp.common.util.ResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  鉴权失败的异常可以通过全局异常捕获，也可以使用security自己提供的AccessDeniedHandler进行处理
 */
//@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 记录鉴权失败日志
        System.out.println("鉴权失败：" + accessDeniedException.getMessage());

        // 设置响应状态码，例如403 Forbidden
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 返回给前端的JSON响应，根据实际情况调整

//        response.getWriter().write(JSON.toJSONString(ResultUtil.fail(ResultCode.NO_PERMISSION)));
        objectMapper.writeValue(response.getWriter(),  ResultUtil.fail(ResultCode.NO_PERMISSION));


        // 或者重定向到无权限提示页面
        // response.sendRedirect("/access-denied");
    }
}