package com.sp.common.enums;

/**
 * 响应结果
 * @version 1.0
 * @createDate 2021/03/04 16:58
 */
public enum ResultCode {

    /* 成功 */
    SUCCESS(200, "成功"),
    /* 默认失败 */
    FAIL(500, "系统错误"),
    /* 参数错误：1000～1999 */
    PARAM_ERROR(1001, "参数错误"),
    /* 未登录 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    /* 没有权限 */
    NO_PERMISSION(3001, "没有权限");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}