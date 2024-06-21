package com.sp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.model.entity.Test;
import com.sp.service.TestService;
import com.sp.mapper.TestMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【test】的数据库操作Service实现
* @createDate 2024-06-21 20:21:20
*/
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test>
    implements TestService{

}




