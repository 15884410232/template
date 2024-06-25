package com.sp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.model.entity.UserRole;
import com.sp.service.UserRoleService;
import com.sp.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 13967
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2024-06-25 14:38:15
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




