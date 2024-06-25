package com.sp.service;

/**
 * @author chenqi
 * @version 1.0
 * @createDate 2022/03/24 17:38
 * @see com.sp.service
 */
public interface AppInitService {


    /**
     * 初始化角色和管理员账号
     */
    void initRole();

    /**
     * 初始化权限
     */
    void initPermission();

}
