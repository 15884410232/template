package com.sp.common.util;

import com.alibaba.fastjson.JSON;
import com.sp.common.enums.ResultCodeEnum;
import com.sp.model.dto.response.base.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 返回值工具类
 */
public class ResultUtil {

    public static Result success() {
        return new Result(ResultCodeEnum.SUCCESS,null);
    }

    public static <T> Result<T> success(T data) {
        return new Result(ResultCodeEnum.SUCCESS,data);
    }

    public static Result fail() {
        return new Result(ResultCodeEnum.FAIL,null);
    }

    public static Result fail(ResultCodeEnum resultCodeEnum) {

        return new Result(resultCodeEnum,null);
    }
    public static Result fail(String message) {
        return new Result(message);
    }

    public static void writeFail(ResultCodeEnum resultCodeEnum, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSON.toJSONString(fail(resultCodeEnum)));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }
}
