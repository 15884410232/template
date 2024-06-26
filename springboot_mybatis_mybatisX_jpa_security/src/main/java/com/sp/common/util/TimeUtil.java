package com.sp.common.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static Date nexetSecond(Date date, int second){
        Calendar calendar = Calendar.getInstance();
        if(date!=null){
            calendar.setTime(date);
        }
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }


    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }
}
