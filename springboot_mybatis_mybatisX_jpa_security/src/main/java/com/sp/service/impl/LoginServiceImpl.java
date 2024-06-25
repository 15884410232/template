package com.sp.service.impl;

import com.sp.common.util.JwtUitl;
import com.sp.common.util.RedisUtil;
import com.sp.common.util.ResultUtil;
import com.sp.config.security.MyUserDetails;
import com.sp.mapper.PermissionMapper;
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
    private JwtUitl jwtUitl;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public Result doLogin(LoginDto loginDto) {

        //用户在登录页面输入的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        //获取AuthenticationManager的authenticate方法来进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断上面那行的authenticate是否为null，如果是则认证没通过，就抛出异常
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过，就使用userid生成一个jwt，然后把jwt存入ResponseResult后返回
        MyUserDetails loginUser = (MyUserDetails) authenticate.getPrincipal();
        String username = loginUser.getUsername();
        Long userid = loginUser.getUser().getId();
        String token = jwtUitl.generateToken(username, String.valueOf(userid));
        //设置权限
        List<String> permissions = permissionMapper.findAllByUserId(userid);
        loginUser.setPermissions(permissions);
        //把完整的用户信息存入redis
        redisUtil.set(token,loginUser);
        LoginSuccessVo loginSuccessVo=new LoginSuccessVo();
        loginSuccessVo.setToken(token);
        loginSuccessVo.setPermissions(permissions);
        return ResultUtil.success(loginSuccessVo);
    }
}

