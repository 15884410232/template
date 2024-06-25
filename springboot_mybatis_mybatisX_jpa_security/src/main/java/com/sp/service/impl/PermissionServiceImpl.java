package com.sp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.model.entity.Permission;
import com.sp.service.PermissionService;
import com.sp.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author 13967
* @description 针对表【permission】的数据库操作Service实现
* @createDate 2024-06-25 15:26:51
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




