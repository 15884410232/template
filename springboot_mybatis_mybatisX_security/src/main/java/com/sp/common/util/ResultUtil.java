package com.sp.common.util;

import com.sp.common.enums.ResultCode;
import com.sp.model.dto.response.Result;

/**
 * 返回值工具类
 */
public class ResultUtil {

    public static Result success() {
        return new Result(ResultCode.SUCCESS,null);
    }

    public static <T> Result<T> success(T data) {
        return new Result(ResultCode.SUCCESS,data);
    }

    public static Result fail() {
        return new Result(ResultCode.FAIL,null);
    }

    public static Result fail(ResultCode resultCode) {

        return new Result(resultCode,null);
    }
    public static Result fail(String message) {
        return new Result(message);
    }
}
