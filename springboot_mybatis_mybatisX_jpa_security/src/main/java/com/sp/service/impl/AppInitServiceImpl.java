package com.sp.service.impl;

import com.sp.common.enums.PermissionType;
import com.sp.common.enums.RoleEnum;
import com.sp.common.enums.SourcePlatEnum;
import com.sp.common.util.PasswordEncoderUtil;
import com.sp.config.myParam.MyParam;
import com.sp.mapper.*;
import com.sp.model.entity.*;
import com.sp.service.AppInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * 系统初始化
 *
 * @author chenqi
 * @version 1.0
 * @createDate 2022/03/24 17:38
 * @see com.sp.service.impl
 */
@Slf4j
@Service
public class AppInitServiceImpl implements AppInitService {

//    @Value("${myparam.admin.username}")
//    private String username;

    @Resource
    private MyParam myParam;



    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 初始化角色
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void initRole() {
        //如果还没有初始化角色，则初始化角色表
        //初始化管理员
        if (roleMapper.findOneByCode(RoleEnum.admin.getCode()) == null) {
            //更新
            Role admin = new Role();
            admin.setCode(RoleEnum.admin.getCode());
            admin.setName(RoleEnum.admin.getName());
            admin.setCreateBy(0L);
            admin.setUpdateBy(0L);
            roleMapper.insert(admin);
            if (log.isInfoEnabled()) {
                log.info("初始化" + RoleEnum.admin.getName() + "角色");
            }
            User adminUser=new User();
            adminUser.setUsername(myParam.getAdmin().getUsername());
            adminUser.setPassword(PasswordEncoderUtil.encodePassword(myParam.getAdmin().getPassword()));
            adminUser.setMobile(myParam.getAdmin().getMobile());
            adminUser.setEmail(myParam.getAdmin().getEmail());
            adminUser.setNickname(myParam.getAdmin().getNickname());
            adminUser.setCreateBy(0L);
            adminUser.setUpdateBy(0L);
            userMapper.insert(adminUser);

            UserRole UserRole=new UserRole();
            UserRole.setUserId(adminUser.getId());
            UserRole.setRoleId(admin.getId());
            userRoleMapper.insert(UserRole);

        }
        //初始化普通角色
        if (roleMapper.findOneByCode(RoleEnum.normal.getCode()) == null) {
            Role normal = new Role();
            normal.setCode(RoleEnum.normal.getCode());
            normal.setName(RoleEnum.normal.getName());
            normal.setCreateBy(0L);
            normal.setUpdateBy(0L);
            roleMapper.insert(normal);
            if (log.isInfoEnabled()) {
                log.info("初始化普通" + RoleEnum.normal.getName() + "角色");
            }
        }
    }

    /**
     * 初始化权限
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void initPermission() {
        //初始化前台系统根节点
        Permission front = permissionMapper.findOneByCode(SourcePlatEnum.front.getCode());
        if (front == null) {
            front = new Permission();
            front.setCode(SourcePlatEnum.front.getCode());
            front.setName(SourcePlatEnum.front.getName());
            front.setSourcePlat(SourcePlatEnum.front.getCode());
            front.setType(PermissionType.menu.getCode());
            front.setUrl("/");
            front.setParentId(-1L);
            front.setCreateBy(0L);
            front.setUpdateBy(0L);
            permissionMapper.insert(front);
        }

        //初始化后台台系统根节点
        Permission back = permissionMapper.findOneByCode(SourcePlatEnum.back.getCode());
        if (back == null) {
            back = new Permission();
            back.setCode(SourcePlatEnum.back.getCode());
            back.setName(SourcePlatEnum.back.getName());
            back.setSourcePlat(SourcePlatEnum.back.getCode());
            back.setType(PermissionType.menu.getCode());
            back.setUrl("/");
            back.setParentId(-1L);
            back.setCreateBy(0L);
            back.setUpdateBy(0L);
            permissionMapper.insert(back);
        }

        RolePermission frontRolePermission = rolePermissionMapper.findOneByRoleIdAndPermissionId(roleMapper.findOneByCode(RoleEnum.admin.getCode()).getId(), front.getId());
        if (frontRolePermission == null) {
            frontRolePermission = new RolePermission();
            frontRolePermission.setRoleId(roleMapper.findOneByCode(RoleEnum.admin.getCode()).getId());
            frontRolePermission.setPermissionId(front.getId());
            frontRolePermission.setCreateBy(0L);
            frontRolePermission.setUpdateBy(0L);
            rolePermissionMapper.insert(frontRolePermission);
        }

        RolePermission backRolePermission = rolePermissionMapper.findOneByRoleIdAndPermissionId(roleMapper.findOneByCode(RoleEnum.admin.getCode()).getId(), back.getId());
        if (backRolePermission == null) {
            backRolePermission = new RolePermission();
            backRolePermission.setRoleId(roleMapper.findOneByCode(RoleEnum.admin.getCode()).getId());
            backRolePermission.setPermissionId(back.getId());
            backRolePermission.setCreateBy(0L);
            backRolePermission.setUpdateBy(0L);
            rolePermissionMapper.insert(backRolePermission);
        }



        //初始化后台台系统根节点
        Permission userManager = permissionMapper.findOneByCode("userManager");
        if (userManager == null) {
            userManager = new Permission();
            userManager.setCode("userManager");
            userManager.setName("用户管理");
            userManager.setSourcePlat(SourcePlatEnum.back.getCode());
            userManager.setType(PermissionType.menu.getCode());
            userManager.setUrl("/");
            userManager.setParentId(back.getId());
            userManager.setCreateBy(0L);
            userManager.setUpdateBy(0L);
            permissionMapper.insert(userManager);
        }

        RolePermission userManagerRolePermission = rolePermissionMapper.findOneByRoleIdAndPermissionId(roleMapper.findOneByCode(RoleEnum.admin.getCode()).getId(), userManager.getId());
        if (userManagerRolePermission == null) {
            userManagerRolePermission = new RolePermission();
            userManagerRolePermission.setRoleId(roleMapper.findOneByCode(RoleEnum.admin.getCode()).getId());
            userManagerRolePermission.setPermissionId(userManager.getId());
            userManagerRolePermission.setCreateBy(0L);
            userManagerRolePermission.setUpdateBy(0L);
            rolePermissionMapper.insert(userManagerRolePermission);
        }

        //初始化后台台系统根节点
        Permission addUser = permissionMapper.findOneByCode("addUser");
        if (addUser == null) {
            addUser = new Permission();
            addUser.setCode("addUser");
            addUser.setName("新增用户");
            addUser.setSourcePlat(SourcePlatEnum.back.getCode());
            addUser.setType(PermissionType.menu.getCode());
            addUser.setUrl("/");
            addUser.setParentId(userManager.getId());
            addUser.setCreateBy(0L);
            addUser.setUpdateBy(0L);
            permissionMapper.insert(addUser);
        }

        RolePermission addUserRolePermission = rolePermissionMapper.findOneByRoleIdAndPermissionId(roleMapper.findOneByCode(RoleEnum.admin.getCode()).getId(), addUser.getId());
        if (addUserRolePermission == null) {
            addUserRolePermission = new RolePermission();
            addUserRolePermission.setRoleId(roleMapper.findOneByCode(RoleEnum.admin.getCode()).getId());
            addUserRolePermission.setPermissionId(addUser.getId());
            addUserRolePermission.setCreateBy(0L);
            addUserRolePermission.setUpdateBy(0L);
            rolePermissionMapper.insert(addUserRolePermission);
        }
    }
}
