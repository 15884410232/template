package com.sp.common.exception;

/**
 * @author chenqi
 * @date 2022/3/21
 */
public class CommonException extends RuntimeException{
    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
    }
}
