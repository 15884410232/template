package com.sp.model.dto.req;

import com.sp.model.dto.req.base.PageReq;
import lombok.Data;

@Data
public class TestReq {

    private String name;

    PageReq pageReq=new PageReq();

}
