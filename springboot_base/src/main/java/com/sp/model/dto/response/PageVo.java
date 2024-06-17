package com.sp.model.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 分页查询返回参数
 * Created by   on 2021/6/6.
 */
@Data
public class PageVo<T> implements Serializable {

    private int page=1;

    private int row=20;

    private BigInteger total=BigInteger.ZERO;

    private List<T> Data;
}
