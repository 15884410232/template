package com.sp.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {

    private String base64Secret;

    private Integer maxIdleSecond;

    private Integer maxLiveSecond;
}
