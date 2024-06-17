package com.sp.common.util;


import com.sp.model.dto.req.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUerUitl {


    public static LoginUser getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser)authentication.getPrincipal();
        return user;

    }

}
