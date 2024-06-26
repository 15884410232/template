package com.sp.common.util;

import com.sp.common.exception.CommonException;
import com.sp.config.jwt.JwtConfig;
import com.sp.config.jwt.JwtUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class JwtUitl {

    @Resource
    private JwtConfig jwtConfig;

    public String generateToken(String username, String UserId) {
        Date date = TimeUtil.nexetSecond(new Date(), jwtConfig.getMaxLiveSecond());
//        Date date = TimeUtil.nexetSecond(new Date(), 10);
        JwtBuilder jwtBuilder= Jwts.builder();
        jwtBuilder.setSubject(username);
        jwtBuilder.setId(UserId);
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setExpiration(date);
        jwtBuilder.signWith(SignatureAlgorithm.HS512,jwtConfig.getBase64Secret());
        return jwtBuilder.compact();
    }


    public JwtUser parseToken(String token) {
        JwtUser jwtUser = new JwtUser();
        try {
            JwtParser jwtParser = Jwts.parser().setSigningKey(jwtConfig.getBase64Secret());
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
}
