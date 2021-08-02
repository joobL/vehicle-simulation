package com.muyu;

import com.muyu.utils.BaseUtils;

import java.util.Arrays;

/**
 * @author 牧鱼
 * @Classname Test
 * @Description TODO
 * @Date 2021/8/1
 */
public class Test {
    public static void main(String[] args) {
        String str = "牧VIN87863712375900SXDY1228105056冀A7X7E678.9";

        System.out.println(str);
        System.out.println(Arrays.toString(str.getBytes()));

        String s = BaseUtils.byteToStr(str.getBytes());
        System.out.println("10进制字符："+s);
        /*byte[] bytes = BaseUtils.tenStrToHexByte(s);
        String s1 = BaseUtils.byteToStr(bytes);
        System.out.println("16进制字符："+s1);
        String s2 = BaseUtils.hexStrToTenStr(s1);
        System.out.println(s2);*/
    }
}




