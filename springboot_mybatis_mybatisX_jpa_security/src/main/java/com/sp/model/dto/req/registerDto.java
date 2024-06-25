package com.sp.model.dto.req;

import lombok.Data;

/**
 * 注册请求
 */
@Data
public class registerDto {

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private String status;

    /**
     * 用户名
     */
    private String username;

}
