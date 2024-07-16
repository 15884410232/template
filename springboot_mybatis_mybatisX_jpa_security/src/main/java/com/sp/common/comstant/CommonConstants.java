package com.sp.common.comstant;

import com.sp.common.enums.MyUrlEnum;

public class CommonConstants {


    public static final String[] passUrl
            = {
                    MyUrlEnum.login.getPath(),
                    MyUrlEnum.pass.getPath()};


    public static class ApiUrl{
        public static final String[] SKIP_SIGN = new String[]{
                MyUrlEnum.login.getPath(),
                MyUrlEnum.pass.getPath(),
                "/pass/captcha"
        };
    }

}
