package com.sp.common.enums;

/**
 * 系统
 * @author chenqi
 * @date 2022/3/27
 */
public enum SourcePlat {
    /**
     * 前台
     */
    front("front","前台"),

    /**
     * 按钮
     */
    back("back","后台");

    /**
     * 系统代码
     */
    private String code;

    /**
     * 系统名称
     */
    private String name;

    SourcePlat(String code, String name){
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
}
