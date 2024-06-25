package com.sp.common.enums;

/**
 *  用户状态枚举 code desc
 */
public enum UserStatusEnum {
    NORMAL("0", "正常"),
    LOCK("1", "锁定"),
    DELETE("2", "异常");

    private String code;
    private String desc;

    UserStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
    public static String getDescByCode(String code) {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getCode().equals(code)) {
                return userStatusEnum.getDesc();
            }
        }
        return null;
    }

}
