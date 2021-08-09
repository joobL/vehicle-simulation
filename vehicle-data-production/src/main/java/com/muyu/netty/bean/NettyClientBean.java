package com.muyu.netty.bean;

/**
 * @author 牧鱼
 * @Classname NettyClientBean
 * @Description TODO
 * @Date 2021/8/3
 */
public class NettyClientBean {

    private String host ;

    private int port;

    public NettyClientBean() {
    }

    public NettyClientBean(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
