package com.sp.common.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;


public class MyAuthenticationException extends AuthenticationException {
    @Getter
    @Setter
    private String message;

    public MyAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public MyAuthenticationException(String msg) {
        super(msg);
    }
}