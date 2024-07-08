package com.sp.model.dto.response.base;

import com.sp.common.enums.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 封装返回的json对象
 * @version 1.0
 * @createDate 2021/03/04 17:00
 */

@Data
//@NoArgsConstructor //生成无参构造函数
//@AllArgsConstructor //生成全参数构造函数
public class Result<T> implements Serializable {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public Result(){

    }

    public Result(ResultCodeEnum resultCodeEnum, T data) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        this.data = data;
    }

    public Result(String message) {
        this.code = ResultCodeEnum.FAIL.getCode();
        this.message = message;
    }

}