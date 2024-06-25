package com.sp.common.enums;

/**
 * 权限类型
 * @author chenqi
 * @date 2022/3/27
 */
public enum PermissionType {

    /**
     * 菜单
     */
    menu("menu","菜单"),

    /**
     * 按钮
     */
    button("button","按钮"),

    /**
     * 接口
     */
    backApi("backApi","接口");

    /**
     * 权限类型代码
     */
    private String code;

    /**
     * 权限类型名称
     */
    private String name;

    PermissionType(String code, String name){
        this.code=code;
        this.name=name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
