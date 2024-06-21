package com.sp.service;

import com.sp.model.entity.UserBa;
import com.sp.model.entity.UserBaExample;
import com.sp.model.mapper.UserBaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserBaMapper userBaMapper;

    @Transactional
    public List<UserBa> getUserList(){
        UserBaExample userExample=new UserBaExample();
        userExample.createCriteria().andNameEqualTo("Tom");
        List<UserBa> userBas = userBaMapper.selectByExample(userExample);
        return userBas;
    }


}
