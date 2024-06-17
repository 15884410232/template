package com.sp.service;

import com.sp.model.dto.TestDto;
import com.sp.model.dto.req.TestReq;
import com.sp.model.dto.req.base.PageReq;
import com.sp.model.dto.response.PageVo;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public PageVo<TestDto> getTestPage(PageReq<TestReq> pageReq){

        return new PageVo<>();
    }

}
