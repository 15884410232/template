package com.sp.model.dto.base;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 分页查询
 * Created by   on 2021/6/6.
 */
@Data
public class PageVo<T> implements Serializable {

    private int row=20;
    private int page=1;
    private BigInteger total=BigInteger.ONE;
    private List<T> Data;
}
