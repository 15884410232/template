package com.sp.contoller;

import com.sp.model.dto.req.LoginDto;
import com.sp.model.dto.base.Result;
import com.sp.service.LoginService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("account")
public class AccountController {


    @Resource
    private LoginService loginService;

    @RequestMapping("login")
    public Result login(@RequestBody LoginDto loginDto){
        return loginService.doLogin(loginDto);


//        if(StrUtil.hasBlank(loginDto.getUsername())||StrUtil.hasBlank(loginDto.getPassword())){
//            throw new CommonException("参数错误");
//        }
//        String username=loginDto.getUsername();
//        String password=loginDto.getPassword();
//        List<User> users = userDao.selectByUsername(username);
//
//        if(users.size()==0){
//            throw new CommonException("用户不存在");
//        }
//
//        User user=users.get(0);
//
//        //生成token
//        String token= jwtUitl.generateToken(user.getId(),user.getUsername());
//        redisUtil.set(user.getId(),user);
//        LoginSuccessVo loginSuccessVo=new LoginSuccessVo();
//
//        loginSuccessVo.setToken(token);
//        ResultUtil.success(loginSuccessVo);

    }
}
