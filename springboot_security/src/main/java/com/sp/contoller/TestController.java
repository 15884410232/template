package com.sp.contoller;

import com.sp.common.util.CurrentUerUitl;
import com.sp.common.util.ResultUtil;
import com.sp.common.exception.CommonException;
import com.sp.model.dto.base.Result;
import com.sp.model.dto.req.LoginUser;
import com.sp.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author chenqi
 * @date 2022/3/19
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController{

    @Autowired
    private TestService testService;

    @Resource
    private JedisPool jedisPool;

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/one")
    public Result one(String name){
        log.info("namteste:{}",name);
        testService.saveTest();
        testService.getTest();
        LoginUser currentUser = CurrentUerUitl.getCurrentUser();

//        if(true) {
//            throw new CommonException("dadad");
//        }
        return ResultUtil.success();
    }

    @PreAuthorize("hasAuthority('test')")
    @RequestMapping("/set")
    public Result set(String name){
//        redisTemplate.opsForValue().set("account","123");
        jedisPool.getResource().set("test","323232");

        return ResultUtil.success();
    }

    @RequestMapping("/get")
    public Result get(String name){
//        String account = (String)redisTemplate.opsForValue().get("account");
        String test = jedisPool.getResource().get("test");
        return ResultUtil.success(test);
    }



}
