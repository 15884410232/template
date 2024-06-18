package com.sp.common.util;

/**
 * @author chenqi
 * @date 2022/3/24
 */
public class StringUtils {

    public static boolean isBlank(String str){
        if(str==null||str.toCharArray().length==0){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }
}
