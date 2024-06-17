package com.sp.service.impl;

import com.sp.model.dao.UserDao;
import com.sp.model.dto.req.LoginUser;
import com.sp.model.entity.sys.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * UserDetailsService接口有很多的实现类，但为什么SpringSecurity会使用我们自定义的实现类？
 * 因为MyUserDetailServiceImpl被标记为“@Service”,它会被Spring容器管理， 当Spring Security需要进行用户认证时，它会自动查找已经被Spring扫描并管理的MyUserDetailServiceImpl实例，并使用它来处理认证请求。
 */

/**
 * @author 35238
 * @date 2023/7/11 0011 20:39
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    //UserDetails是Security官方提供的接口
    public UserDetails loadUserByUsername(String xxusername) throws UsernameNotFoundException {

        //查询用户信息。我们写的userMapper接口里面是空的，所以调用的是mybatis-plus提供的方法
        List<UserEntity> users = userDao.selectByUsername(xxusername);
        //如果用户传进来的用户名，但是数据库没有这个用户名，就会导致我们是查不到的情况，那么就进行下面的判断。避免程序安全问题
        if(Objects.isNull(users)){//判断user对象是否为空。当在数据库没有查到数据时，user就会为空，也就会进入这个判断
            throw new RuntimeException("用户名或者密码错误");
        }

        //--------------------------------查询用户权限信息---------------------------------

        //由于我们自定义了3个权限，所以用List集合存储。注意权限实际就是'有特殊含义的字符串'，所以下面的三个字符串就是自定义的
        //下面那行就是我们的权限集合，等下还要在LoginUser类做权限集合的转换
        List<String> list = new ArrayList<>(Arrays.asList("test","adminAuth","huanfAuth"));
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntity.setPassword("$2a$10$llaGRG.n06PgEyEdHLgyIOfBKVFXVI0/RELT6XCZXQHXNPOHze1Ju");
        userEntity.setId("21223");
        users.add(userEntity);
        //------------------------------------------------------------------------------

        //把查询到的user结果，封装成UserDetails类型，然后返回。
        //但是由于UserDetails是个接口，所以我们先需要在domino目录新建LoginUser类，作为UserDetails的实现类，再写下面那行
        return new LoginUser(users.get(0));
        //这里传了第二个参数，表示的是权限信息
    }
}