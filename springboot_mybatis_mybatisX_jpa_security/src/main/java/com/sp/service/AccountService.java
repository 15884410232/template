package com.sp.service;

import com.sp.model.dto.response.PermissionVo;

import java.util.List;

public interface AccountService {

    public List<PermissionVo> getMenu(Long userId, Long roleId);

}
