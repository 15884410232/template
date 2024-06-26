package com.sp.service.cache;

import com.sp.model.entity.User;
import com.sp.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserCacheService {

    @Resource
    private UserService userService;

    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        // 从数据库中读取用户信息
        User user = userService.getById(id);
        return  user;
    }

}
