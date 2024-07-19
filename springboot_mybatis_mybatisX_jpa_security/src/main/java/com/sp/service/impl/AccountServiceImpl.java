package com.sp.service.impl;

import com.sp.mapper.PermissionMapper;
import com.sp.model.entity.Permission;
import com.sp.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl  implements AccountService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getMenu(Long userId,Long roleId) {

        return permissionMapper.getMenu(userId,roleId);
    }
}
