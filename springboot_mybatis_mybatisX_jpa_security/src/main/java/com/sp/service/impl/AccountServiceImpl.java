package com.sp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.sp.common.util.tree.TreeUtil;
import com.sp.mapper.PermissionMapper;
import com.sp.model.dto.response.PermissionVo;
import com.sp.model.entity.Permission;
import com.sp.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl  implements AccountService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionVo> getMenu(Long userId,Long roleId) {

        List<Permission> menu = permissionMapper.getMenu(userId, roleId);
        List<PermissionVo> menus=new ArrayList<>();
        for (Permission permission : menu) {
            PermissionVo permissionVo = new PermissionVo();
            // 复制
            BeanUtil.copyProperties(permission,permissionVo);
            permissionVo.setChildren(new ArrayList<>());
            menus.add(permissionVo);
        }
        return TreeUtil.buildTree(menus,-1L);

    }
}
