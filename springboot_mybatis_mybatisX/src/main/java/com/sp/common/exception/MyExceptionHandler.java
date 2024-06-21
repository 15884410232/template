package com.sp.common.exception;

import com.sp.common.util.ResultUtil;
import com.sp.model.dto.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * @version 1.0
 * @createDate 2021/08/25 10:26
 */
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value =Exception.class)
    public Result<Object> exceptionHandler(Exception ex){
        log.info("exception-----------------------");
        ex.printStackTrace();
        return ResultUtil.fail(ex.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(value =CommonException.class)
    public Result<Object> commonExceptionHandler(Exception ex){
        log.info("exception-----------------------");
        ex.printStackTrace();
        return ResultUtil.fail(ex.getMessage());
    }
}
