package com.muyu.netty.operate;


import com.muyu.common.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author 牧鱼
 * @Classname NettyClient
 * @Description TODO
 * @Date 2021/7/30
 */
public class NettyClientMsg {

    private static final Logger log = LoggerFactory.getLogger(NettyClientMsg.class);

    /**
     * 发送消息
     * @param msg
     */
    public static void sendMsg(String msg){
        log.info("客户端发送消息：{}",msg);
        Config.ctx.writeAndFlush(msg + Config.DATA_PACK_SEPARATOR);
    }

    /**
     * 销毁netty
     */
    public static void destroy(){
        log.info("客户端断开连接");
        Config.ctx.writeAndFlush("close"+ Config.DATA_PACK_SEPARATOR);
        Config.ctx = null;
    }

}
