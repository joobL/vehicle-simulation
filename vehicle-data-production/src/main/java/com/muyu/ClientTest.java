package com.muyu;

import com.muyu.netty.client.NettyClient;

/**
 * @author 牧鱼
 * @Classname Test
 * @Description TODO
 * @Date 2021/7/30
 */
public class ClientTest {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            NettyClient nettyClient = new NettyClient("127.0.0.1", 8564);
            nettyClient.init();
        }).start();

    }
}
