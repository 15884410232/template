package com.sp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.common.enums.ResultCodeEnum;
import com.sp.common.util.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint,  HandlerExceptionResolver {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    /**
     * 在用户未通过认证尝试访问受保护资源时被调用。resolveException 则是在控制器方法执行过程中遇到任何未被捕获的异常时被调用。
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 将错误信息转化为JSON字符串并写入响应体
        objectMapper.writeValue(response.getWriter(),  ResultUtil.fail(ResultCodeEnum.USER_NOT_LOGIN));
    }



    // HandlerExceptionResolver 接口方法，这里用于额外捕获和处理非标准流程中的异常
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 其他异常可选择性处理或忽略
        return null;
    }
}