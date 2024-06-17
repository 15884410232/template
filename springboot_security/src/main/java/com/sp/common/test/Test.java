package com.sp.common.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Object b=null;
        aa a=(aa)b;
        System.out.println("dad");


        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        System.out.println(        bCryptPasswordEncoder.encode("tests"));
    }
    class aa{
        int a;
    }
    class bb extends aa{
        int a;
    }
}
