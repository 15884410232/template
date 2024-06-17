package com.sp.controller;

import com.sp.common.util.ResultUtil;
import com.sp.model.dto.req.TestReq;
import com.sp.model.dto.req.base.PageReq;
import com.sp.model.dto.response.Result;
import com.sp.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenqi
 * @date 2022/3/19
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;


    @RequestMapping("/one")
    public Result<Object> one(PageReq<TestReq> pageReq){
        log.info("pageReq:{}",pageReq);
        return ResultUtil.success(testService.getTestPage(pageReq));
    }

}
