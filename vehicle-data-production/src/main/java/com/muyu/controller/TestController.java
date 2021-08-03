package com.muyu.controller;

import com.muyu.common.Config;
import com.muyu.netty.client.NettyClientBean;
import com.muyu.netty.client.NettyClientInit;
import com.muyu.netty.client.NettyClientMsg;
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
@RequestMapping("/nettyTest")
public class TestController {

    @PostMapping("/initNettyClient")
    public Map initNettyClient(@RequestBody NettyClientBean nettyClientBean){
        new Thread(() -> {
            NettyClientInit.nettyInit(nettyClientBean);
        }).start();
        return new HashMap<String,String>(){{
           put("status","1");
           put("msg","操作成功");
        }};
    }

    @PostMapping("/nettySendMsg")
    public Map sendNettyMsg(@RequestBody String msg){
        if (Config.ctx == null){
            return new HashMap<String,String>(){{
                put("status","0");
                put("msg","未连接netty服务器");
            }};
        }
        NettyClientMsg.sendMsg(msg);
        return new HashMap<String,String>(){{
            put("status","1");
            put("msg","操作成功");
        }};
    }



    @GetMapping("/nettyDestroy")
    public Map nettyDestroy(String msg){
        if (Config.ctx == null){
            return new HashMap<String,String>(){{
                put("status","0");
                put("msg","未连接netty服务器");
            }};
        }
        NettyClientMsg.destroy();
        return new HashMap<String,String>(){{
            put("status","1");
            put("msg","操作成功");
        }};
    }
}
