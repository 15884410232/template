package com.sp.common.util;

import com.alibaba.fastjson.JSON;
import com.sp.common.enums.ResultCode;
import com.sp.model.dto.response.base.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    public static void writeFail(ResultCode resultCode, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSON.toJSONString(fail(resultCode)));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }
}
