package com.sp.common.enums;

/**
 * 响应结果
 * @version 1.0
 * @createDate 2021/03/04 16:58
 */
public enum ResultCode {

    SUCCESS(200, "成功"),

    FAIL(500, "系统错误"),

    PARAM_ERROR(400, "参数错误"),

    USER_NOT_LOGIN(401, "账号已过期或未登录"),

    USER_TIME_OUT(401, "账号超时，请重新登录"),

    USER_EXPIRE(401, "账号已在其他地方登录，请重新登录"),

    NO_PERMISSION(403, "权限不足");

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