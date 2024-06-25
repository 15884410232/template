package com.sp.service;


import com.sp.model.dto.req.LoginDto;
import com.sp.model.dto.response.base.Result;

public interface LoginService {

    Result doLogin(LoginDto loginDto);
}
