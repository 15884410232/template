package com.sp.config.myParam;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "myparam")
public class MyParam {

    @Setter
    @Getter
    private Admin admin;

    @Data
    public static class Admin{
        /**
         *  用户名
         */
        private String username;

        /**
         *  昵称
         */
        private String nickname;

        /**
         *  密码
         */
        private String password;
        /**
         *  邮箱
         */
        private String email;
        /**
         *  手机号
         */
        private String mobile;
        /**
         *  状态
         */
        private int status;
    }

}
