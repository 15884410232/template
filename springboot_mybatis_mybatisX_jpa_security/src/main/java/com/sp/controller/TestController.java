package com.sp.controller;

import com.sp.common.util.ResultUtil;
import com.sp.model.dto.response.base.Result;
import com.sp.model.entity.User;
import com.sp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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


    /**
     * getUserList
     * @return
     */
//    @PreAuthorize("hasAuthority('getUserList')")
    @RequestMapping("/getUserList")
    public Result<Object> getUserList(HttpServletResponse response){
        List<User> list = userService.list();
        return ResultUtil.success(list);
    }

    /**
     * getUserList
     * @return
     */
    @PreAuthorize("hasAuthority('front')")
    @RequestMapping("/addUser")
    public Result<Object> addUser(User user){
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResultUtil.success(userService.save(user));

    }


    /**
     * getUserList
     * @return
     */
    @RequestMapping("/updateUser")
    public Result<Object> updateUser(@RequestBody User user){

        User byId = userService.getById(user.getId());
        byId.setMobile(user.getMobile());

        return ResultUtil.success(userService.updateById(byId));
    }
}
