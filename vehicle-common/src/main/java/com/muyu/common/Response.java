package com.muyu.common;

import com.muyu.enums.ResponseEnum;
import com.muyu.utils.BaseUtils;

/**
 * @author 牧鱼
 * @Classname Response
 * @Description TODO
 * @Date 2021/8/5
 */
public class Response<T> {

    // 状态
    public int code;

    // 描述
    public String msg;

    // 实体
    public T data;

    /**
     * 静态构造 无法直接创建对象
     */
    private Response() {
    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功操作
     * @return
     */
    public static Response success(){
        return new Response(ResponseEnum.SUCCESS.code, ResponseEnum.SUCCESS.msg, null);
    }



    /**
     * 成功操作 自定义返回描述
     * @return
     */
    public static Response success(String msg){
        return new Response(ResponseEnum.SUCCESS.code, msg, null);
    }

    /**
     * 成功操作 自定义返回实体
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(T data){
        return new Response<T>(ResponseEnum.SUCCESS.code, Response.success().msg, data);
    }

    /**
     * 成功操作 自定义返回描述，返回实体
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(String msg , T data){
        return new Response<T>(ResponseEnum.SUCCESS.code, msg, data);
    }

    /**
     * 失败操作
     * @return
     */
    public static Response error(){
        return new Response(ResponseEnum.ERROR.code, ResponseEnum.SUCCESS.msg, null);
    }


    /**
     * 失败操作 自定义返回描述
     * @return
     */
    public static Response error(String msg){
        return new Response(ResponseEnum.ERROR.code, msg, null);
    }

    /**
     * 失败操作 自定义返回实体
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> error(T data){
        return new Response<T>(ResponseEnum.ERROR.code, Response.success().msg, data);
    }

    /**
     * 失败操作 自定义返回描述，返回实体
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> error(String msg , T data){
        return new Response<T>(ResponseEnum.ERROR.code, msg, data);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
