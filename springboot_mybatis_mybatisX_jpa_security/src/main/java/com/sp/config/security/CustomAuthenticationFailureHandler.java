package com.sp.config.security;

/**
 * 登录失败处理器
 */
import com.alibaba.fastjson.JSON;
import com.sp.model.dto.response.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 记录日志，可以记录异常信息、用户名等
        log.error("登录失败: " + exception.getMessage());

        // 设置响应状态码
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Result result=new Result();
        result.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        result.setMessage(exception.getMessage());

        // 返回给前端的JSON响应，根据实际情况调整
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(result));

        // 如果需要重定向到特定页面，可以调用super.onAuthenticationFailure方法并设置相应的URL
        // super.onAuthenticationFailure(request, response, exception);
    }
}