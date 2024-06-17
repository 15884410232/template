package com.sp.service;

import com.sp.model.dto.base.Result;
import com.sp.model.dto.req.LoginDto;

public interface LoginService {

    Result doLogin(LoginDto loginDto);
}
