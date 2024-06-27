package com.sp.common.enums;

/**
 * 系统
 * @author chenqi
 * @date 2022/3/27
 */
public enum MyUrlEnum {
    /**
     * 前台
     */
    login("/account/login","登录地址");

    /**
     * 系统代码
     */
    private String path;

    /**
     * 系统名称
     */
    private String name;

    MyUrlEnum(String code, String name){
        this.path=code;
        this.name=name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }
}
