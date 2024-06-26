package com.sp.controller;

import com.sp.common.util.ResultUtil;
import com.sp.model.dto.req.TestReq;
import com.sp.model.dto.response.Result;
import com.sp.service.TestService;
import com.sp.service.UserService;
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

    @Resource
    private UserService userService;

    @RequestMapping("/one")
    public Result<Object> one(TestReq testReq){
        log.info("testReq:{}",testReq);
        return ResultUtil.success(testService.getTestPage(testReq));
    }

    @RequestMapping("/getUserList")
    public Result<Object> getUserList(){
        return ResultUtil.success(userService.getUserList());
    }

}
