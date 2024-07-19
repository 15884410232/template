package com.sp.service;

import com.sp.model.entity.Permission;

import java.util.List;

public interface AccountService {

    public List<Permission> getMenu(Long userId,Long roleId);

}
