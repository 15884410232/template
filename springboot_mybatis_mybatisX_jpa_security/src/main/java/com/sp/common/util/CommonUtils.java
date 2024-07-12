package com.sp.common.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class CommonUtils {

    /**
     * 获取MD5加密的字符串
     */
    public static String getMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }

}
