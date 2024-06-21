package com.sp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sp.common.util.ResultUtil;
import com.sp.model.dto.response.Result;
import com.sp.model.entity.User;
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
    private UserService userService;

    @RequestMapping("/getUserList")
    public Result<Object> getUserList(){
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName,"tom");
        return ResultUtil.success(userService.list(lambdaQueryWrapper));
    }

}
