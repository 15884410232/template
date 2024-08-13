package com.sp.service.impl;

import com.sp.common.util.*;
import com.sp.config.jwt.JwtConfig;
import com.sp.config.security.MyUserDetails;
import com.sp.mapper.PermissionMapper;
import com.sp.mapper.RoleMapper;
import com.sp.mapper.UserMapper;
import com.sp.model.dto.req.LoginDto;
import com.sp.model.dto.response.LoginSuccessVo;
import com.sp.model.dto.response.base.Result;
import com.sp.service.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private JwtConfig jwtConfig;

    @Resource
    private JwtUitl jwtUitl;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;


    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public Result doLogin(LoginDto loginDto) {

        if(StringUtils.isBlank(loginDto.getUsername())){
            return ResultUtil.fail("用户名不能为空");
        }

        if(StringUtils.isBlank(loginDto.getPassword())){
            return ResultUtil.fail("密码不能为空");
        }

//        if(StringUtils.isBlank(loginDto.getCaptcha())){
//            return ResultUtil.fail("验证码不能为空");
//        }
//        if(StringUtils.isBlank(loginDto.getCaptchaId())){
//            return ResultUtil.fail("验证码不能为空");
//        }
//        String captchaText = (String)redisUtil.get(loginDto.getCaptchaId());
//        if(StringUtils.isBlank(captchaText)){
//            return ResultUtil.fail("验证码已过期");
//        }
//        if(captchaText.equals(loginDto.getCaptcha())){
//            redisUtil.del(loginDto.getCaptchaId());
//        }else {
//            return ResultUtil.fail("验证码错误");
//        }

        //用户在登录页面输入的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        //获取AuthenticationManager的authenticate方法来进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断上面那行的authenticate是否为null，如果是则认证没通过，就抛出异常
        if(Objects.isNull(authenticate)||!authenticate.isAuthenticated()){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过，就使用userid生成一个jwt，然后把jwt存入ResponseResult后返回
        MyUserDetails loginUser = (MyUserDetails) authenticate.getPrincipal();
        String username = loginUser.getUsername();
        Long userid = loginUser.getUser().getId();
        String token = jwtUitl.generateToken(username, String.valueOf(userid));
        //设置权限
        List<String> permissions = permissionMapper.findAllByUserId(userid);
        List<String> roles=roleMapper.findAllByUserId(userid);
        //放入角色
        for (String role : roles) {
            permissions.add("ROLE_"+role);
        }
        loginUser.setPermissions(permissions);
        //把完整的用户信息和token存入redis
        redisUtil.set("login_"+userid,loginUser,jwtConfig.getMaxLiveSecond());
        redisUtil.set("token_"+userid,token,jwtConfig.getMaxLiveSecond());
        redisUtil.expire("login_"+userid, jwtConfig.getMaxLiveSecond());
        //设置redis的过期时间为token的最大存活时间
        redisUtil.expire("token_"+userid, jwtConfig.getMaxLiveSecond());

        //设置最大空闲时间
        long now = TimeUtil.getUnixTime();
        redisUtil.set("maxIdel_"+userid,now+jwtConfig.getMaxIdleSecond());

        LoginSuccessVo loginSuccessVo=new LoginSuccessVo();
        loginSuccessVo.setToken(token);
        loginSuccessVo.setPermissions(permissions);
        loginSuccessVo.setRoles(roles);
        return ResultUtil.success(loginSuccessVo);
    }
}

