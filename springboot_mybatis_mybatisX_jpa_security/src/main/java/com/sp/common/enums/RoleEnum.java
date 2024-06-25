package com.sp.common.enums;


import lombok.Getter;

/**
 * 系统基本角色
 * @author chenqi
 * @version 1.0
 * @createDate 2022/03/24 17:40
 * @see com.sp.common.enums
 */
@Getter
public enum RoleEnum {
    /**
     * 管理员
     */
    admin("admin", "管理员"),
    /**
     * 普通用户
     */
    normal("normal", "普通用户");

    /**
     * 角色code
     */
    public String code;
    /**
     * 角色名
     */
    public String name;

    RoleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}

