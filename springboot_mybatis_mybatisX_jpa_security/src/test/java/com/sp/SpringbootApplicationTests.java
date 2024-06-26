package com.sp;

import com.alibaba.fastjson.JSON;
import com.sp.common.exception.CommonException;
import com.sp.common.util.JwtUitl;
import com.sp.common.util.TimeUtil;
import com.sp.config.jwt.JwtUser;
import com.sp.model.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@SpringBootTest
class SpringbootApplicationTests {
    @Resource
    private JwtUitl jwtUitl;

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

    @Test
    public void createPassword(){
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
    }

    @Test
    public void getUnixTime() {
        System.out.println(System.currentTimeMillis() / 1000L);
    }

    @Test
    public void test1() {
        String admin = generateToken("admin", "1805896821782929409");
        System.out.println(admin);
        JwtUser jwtUser = parseToken(admin);
        System.out.println(jwtUser.getUsername());
        System.out.println(jwtUser.getUserId());


    }





    public String generateToken(String username, String UserId) {
        Date date = TimeUtil.nexetSecond(new Date(), 86400);
//        Date date = TimeUtil.nexetSecond(new Date(), 10);
        JwtBuilder jwtBuilder= Jwts.builder();
        jwtBuilder.setSubject(username);
        jwtBuilder.setId(UserId);
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setExpiration(date);
        jwtBuilder.signWith(SignatureAlgorithm.HS512,"2GKkHWdj/gZFf37n9boeWCjnE2MycukKE58xQQMArdU=");
        return jwtBuilder.compact();
    }



    public JwtUser parseToken(String token) {
        JwtUser jwtUser = new JwtUser();
        try {
            JwtParser jwtParser = Jwts.parser().setSigningKey("2GKkHWdj/gZFf37n9boeWCjnE2MycukKE58xQQMArdU=");
            Jwt jwt = jwtParser.parseClaimsJws(token);
            DefaultJws<DefaultClaims> defaultJws = (DefaultJws<DefaultClaims>) jwt;
            DefaultClaims claims = defaultJws.getBody();
            String username = claims.getSubject();
            String userId = claims.getId();
//            String other = (String) claims.get("other");
            claims.getSubject();
            jwtUser.setUserId(userId);
            jwtUser.setUsername(username);
            jwtUser.setPermissions(null);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CommonException("token非法");
        }
        return jwtUser;
    }


    @Test
    public void SecretKeyGenerator(){
        // 生成一个256位（32字节）的随机密钥，HS512支持更长的密钥，但256位已足够安全
        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes); // 使用SecureRandom生成随机字节

        // 将字节数组转换为Base64编码的字符串，便于查看和存储
        String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);

        // 创建Key对象，用于HMAC运算
        Key hmacKey = new SecretKeySpec(keyBytes, "HmacSHA512");

        System.out.println("Generated HS512 Secret Key (Base64 Encoded): " + base64EncodedKey);
    }

}
