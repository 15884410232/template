package com.sp.common.test;

import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class HS512KeyGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 生成密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance(SignatureAlgorithm.HS512.getJcaName());
        // 初始化密钥生成器
        keyGenerator.init(256); // HMAC can use 256 bit keys (or any other size as specified in the HMAC RFC)
        // 生成密钥
        SecretKey secretKey = keyGenerator.generateKey();
        // 获取密钥的字节表示
        byte[] keyBytes = secretKey.getEncoded();
        // 转换为Base64字符串（可选，但通常用于在JWT中传递密钥）
        String base64Key = java.util.Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("HS512密钥 (Base64): " + base64Key);
    }
}