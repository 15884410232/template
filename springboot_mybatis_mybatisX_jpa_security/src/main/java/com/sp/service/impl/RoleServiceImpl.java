package com.sp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.model.entity.Role;
import com.sp.service.RoleService;
import com.sp.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 13967
* @description 针对表【role】的数据库操作Service实现
* @createDate 2024-06-25 15:26:50
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




