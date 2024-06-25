package com.sp;

import com.alibaba.fastjson.JSON;
import com.sp.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringbootApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test(){
        Long a=1805173818191265791L;
        User user=new User();
        user.setId(a);

        System.out.println(JSON.toJSONString(user));
        //test
    }

    @org.junit.Test
    public void createPassword(){
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
    }

}
