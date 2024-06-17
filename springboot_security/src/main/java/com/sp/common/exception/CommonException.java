package com.sp.common.exception;

/**
 * @author chenqi
 * @date 2022/3/21
 */
public class CommonException extends RuntimeException{

    private String code;
    private String message;
    public CommonException(String message){
        super(message);
        this.message=message;
    }
    public CommonException(String code,String message){
        super();
        this.code=code;
        this.message=message;

    }

}
