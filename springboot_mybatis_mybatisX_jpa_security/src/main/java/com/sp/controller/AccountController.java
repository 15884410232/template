package com.sp.controller;

import com.sp.model.dto.req.LoginDto;
import com.sp.model.dto.response.base.Result;
import com.sp.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("account")
public class AccountController {


    @Resource
    private LoginService loginService;

    @RequestMapping("doLogin")
    public Result doLogin(@RequestBody LoginDto loginDto){
        log.info("doLogin:{}",loginDto);
        return loginService.doLogin(loginDto);
    }
//
//    @RequestMapping("register")
//    public Result login(LoginDto loginDto){
//        return loginService.doLogin(loginDto);
//    }
}
