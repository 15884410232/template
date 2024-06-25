package com.sp.common.exception;

public class VersionException extends RuntimeException{


    public VersionException() {
        super("并发version异常");
    }

}
