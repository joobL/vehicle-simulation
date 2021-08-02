package com.muyu.config;

import io.netty.channel.Channel;

/**
 * @author 牧鱼
 * @Classname NettyConfig
 * @Description TODO
 * @Date 2021/8/1
 */
public class NettyConfig {


    private static Channel channel = null;

    public static Channel getChannel() {
        return channel;
    }

    public static void setChannel(Channel channel) {
        NettyConfig.channel = channel;
    }
}
