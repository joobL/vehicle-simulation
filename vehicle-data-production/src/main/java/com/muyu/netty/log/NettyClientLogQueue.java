package com.muyu.netty.log;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 牧鱼
 * @Classname NettyLogQueue
 * @Description netty 发送消息队列
 * @Date 2021/8/5
 */
public class NettyClientLogQueue {
    private static LinkedBlockingQueue<String> nettyLogQueue = new LinkedBlockingQueue<String>();

    /**
     * 添加日志消息
     * @param msg
     */
    public static void add(String msg){
        nettyLogQueue.add(msg);
    }

    /**
     * 删除日志消息
     * @return
     */
    public static String get(){
        return nettyLogQueue.poll();
    }
}
