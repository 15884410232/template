package com.sp.model.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 分页查询
 * @author chenqi
 * @param <T>
 */
@Data
public class PageVo<T> implements Serializable {

    /**
     * 默认查询条数
     */
    private int row=20;

    /**
     * 默认页码起始页
     */
    private int page=1;

    /**
     * 记录总条数
     */
    private BigInteger total=BigInteger.ONE;

    /**
     * 数据列表
     */
    private List<T> Data;
}
