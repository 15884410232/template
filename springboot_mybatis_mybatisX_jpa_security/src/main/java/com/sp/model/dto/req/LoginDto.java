package com.sp.model.dto.req;

import lombok.Data;

@Data
public class LoginDto {

    private String username;

    private String password;

    private String captchaId;

    private String captcha;

}
