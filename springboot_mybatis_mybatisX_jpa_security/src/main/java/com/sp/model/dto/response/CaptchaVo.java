package com.sp.model.dto.response;

import lombok.Data;

@Data
public class CaptchaVo {

    private String  captchaId;

    private byte[]  captchaImage;

}
