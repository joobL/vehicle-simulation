package com.muyu.controller;

import com.muyu.common.Response;
import com.muyu.netty.log.NettyClientLogQueue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 牧鱼
 * @Classname NettyLogController
 * @Description TODO
 * @Date 2021/8/5
 */
@RestController()
@RequestMapping("/netty/log")
public class NettyLogController {

    @GetMapping
    public Response<String> getLog(){
        return Response.success(NettyClientLogQueue.get());
    }

}
