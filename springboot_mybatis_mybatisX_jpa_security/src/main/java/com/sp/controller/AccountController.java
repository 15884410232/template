package com.sp.controller;

import com.sp.model.dto.req.LoginDto;
import com.sp.model.dto.response.base.Result;
import com.sp.service.LoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("account")
public class AccountController {


    @Resource
    private LoginService loginService;

    @RequestMapping("login")
    public Result login(LoginDto loginDto){
        return loginService.doLogin(loginDto);
    }
//
//    @RequestMapping("register")
//    public Result login(LoginDto loginDto){
//        return loginService.doLogin(loginDto);
//    }
}
