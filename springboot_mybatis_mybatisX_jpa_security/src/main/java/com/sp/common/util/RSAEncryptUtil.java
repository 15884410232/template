package com.sp.common.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSAEncryptUtil {

    public static void genKeyPair(String[] args) {
        try {
            // 获取KeyPairGenerator实例
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

            // 初始化KeyPairGenerator，密钥长度为2048位
            keyGen.initialize(2048);

            // 生成密钥对
            KeyPair keyPair = keyGen.generateKeyPair();

            // 输出公钥和私钥
            System.out.println("Public Key: " + keyPair.getPublic());
            System.out.println("Private Key: " + keyPair.getPrivate());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
