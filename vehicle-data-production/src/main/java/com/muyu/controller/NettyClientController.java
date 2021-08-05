package com.muyu.controller;

import com.muyu.common.Config;
import com.muyu.common.Response;
import com.muyu.netty.client.NettyClientBean;
import com.muyu.netty.client.NettyClientInit;
import com.muyu.netty.client.NettyClientMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 牧鱼
 * @Classname TestController
 * @Description TODO
 * @Date 2021/8/3
 */
@RestController
@RequestMapping("/nettyClient")
public class NettyClientController {

    @PostMapping("/initNettyClient")
    public Response initNettyClient(@RequestBody NettyClientBean nettyClientBean){
        if (StringUtils.isEmpty(nettyClientBean.getHost())){
            return Response.error("请输入IP地址");
        }
        if (nettyClientBean.getPort() == 0){
            return Response.error("请输入端口号");
        }
        if (Config.ctx != null){
            return Response.error("已与netty服务器建立连接");
        }
        new Thread(() -> {
            NettyClientInit.nettyInit(nettyClientBean);
        }).start();
        return Response.success();
    }

    @PostMapping("/nettySendMsg")
    public Response sendNettyMsg(@RequestBody String msg){
        if (Config.ctx == null){
            return Response.error("未与netty服务器建立连接");
        }
        NettyClientMsg.sendMsg(msg);
        return Response.success();
    }

    @GetMapping("/status")
    public Response nettyStatus(){
        if (Config.ctx == null){
            return Response.error("未与netty服务器建立连接");
        }
        return Response.success();
    }



    @GetMapping("/nettyDestroy")
    public Response nettyDestroy(){
        if (Config.ctx == null){
            return Response.error("未连接netty服务器");
        }
        NettyClientMsg.destroy();
        return Response.success();
    }
}
