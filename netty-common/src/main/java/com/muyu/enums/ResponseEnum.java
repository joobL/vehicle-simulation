package com.muyu.enums;

/**
 * @author 牧鱼
 * @Classname ResponseEnum
 * @Description TODO
 * @Date 2021/8/5
 */
public enum ResponseEnum {

    SUCCESS(100,"操作成功"),
    ERROR(150,"操作异常");

    public int code;

    public String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
