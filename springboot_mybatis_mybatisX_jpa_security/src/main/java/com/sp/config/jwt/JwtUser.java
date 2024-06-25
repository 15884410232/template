package com.sp.config.jwt;

import lombok.Data;

import java.util.List;

@Data
public class JwtUser {

    private String username;

    private String userId;

    private List<String> permissions;

}
