package com.sp.common.exception;

import com.sp.common.enums.ResultCodeEnum;
import com.sp.common.util.ResultUtil;
import com.sp.model.dto.response.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
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
        log.info("CommonException-----------------------");
        ex.printStackTrace();
        return ResultUtil.fail(ex.getMessage());
    }

    /**
     * 鉴权失败的异常可以通过全局异常捕获，也可以使用security自己提供的AccessDeniedHandler进行处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result<Object> accessDeniedException(Exception ex){
        log.info("accessDeniedException-----------------------");
//        ex.printStackTrace();
        return ResultUtil.fail(ResultCodeEnum.NO_PERMISSION);
    }
}
