package com.sp.common.util;

import com.sp.common.exception.CommonException;
import com.sp.config.security.MyUserDetails;
import com.sp.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {
    /**
     * 获取当前登录用户
     */
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal==null){
            throw new CommonException("用户未登录");
        }
        return ((MyUserDetails)principal).getUser();
    }

}
