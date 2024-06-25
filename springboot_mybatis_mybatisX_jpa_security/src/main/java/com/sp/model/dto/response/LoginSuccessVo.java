package com.sp.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class LoginSuccessVo {

    private String token;

    private List<String> permissions;

}
