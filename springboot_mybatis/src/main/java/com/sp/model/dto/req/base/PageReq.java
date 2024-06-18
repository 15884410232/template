package com.sp.model.dto.req.base;

import lombok.Data;

/**
 * 分页查询参数
 */
@Data
public class PageReq<T>  {

    private int page=1;

    private int row=20;

    private T param;
}
