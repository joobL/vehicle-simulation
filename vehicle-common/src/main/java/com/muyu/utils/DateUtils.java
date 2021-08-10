package com.muyu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 牧鱼
 * @Classname DateUtils
 * @Description TODO
 * @Date 2021/8/10
 */
public class DateUtils {
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String getNow(){
        var sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return sdf.format(new Date());
    }
}
