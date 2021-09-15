package com.muyu.controller;

import com.alibaba.fastjson.JSONObject;
import com.muyu.common.Config;
import com.muyu.common.Response;
import com.muyu.mapper.ImitatePositionMapper;
import com.muyu.netty.bean.NettyClientBean;
import com.muyu.netty.client.NettyClientInit;
import com.muyu.netty.log.NettyClientLogQueue;
import com.muyu.netty.operate.NettyClientMsg;
import com.muyu.pojo.VehicleData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 牧鱼
 * @Classname TestController
 * @Description TODO
 * @Date 2021/8/3
 */
@RestController
@RequestMapping("/nettyClient")
public class NettyClientController {

    @Autowired
    private ImitatePositionMapper positionMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

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
    public Response sendNettyMsg(@RequestBody VehicleData vehicleData){
        if (Config.ctx == null){
            return Response.error("未与netty服务器建立连接");
        }
        ListOperations<String, String> operations = redisTemplate.opsForList();
        String s = operations.rightPop(vehicleData.getVin() + "-" + vehicleData.getDrivingRoute());
        if (s != null){
            String[] split = s.split(",");
            vehicleData.setLongitude(split[0]);
            vehicleData.setLatitude(split[1]);
        }
        NettyClientMsg.sendMsg(Config.VEHICLE_MSG_SUF + vehicleData.getVin() +JSONObject.toJSONString(vehicleData));
        NettyClientLogQueue.add(vehicleData.getVehicleMsgLog());
        return Response.success();
    }

    @GetMapping("/sendVehicleStart/{vin}")
    public Response sendVehicleStart(@PathVariable(value = "vin") String vin){
        if (Config.ctx == null){
            return Response.error("未与netty服务器建立连接");
        }
        NettyClientMsg.sendMsg(Config.VEHICLE_START_SUF + vin);
        NettyClientLogQueue.add("<p>VIN:"+vin+" ， 发送启动报文</p>");
        return Response.success();
    }

    @GetMapping("/sendVehicleStop/{vin}")
    public Response sendVehicleStop(@PathVariable(value = "vin") String vin){
        if (Config.ctx == null){
            return Response.error("未与netty服务器建立连接");
        }
        NettyClientMsg.sendMsg(Config.VEHICLE_STOP_SUF + vin);
        NettyClientLogQueue.add("<p>VIN:"+vin+" ， 发送关闭报文</p>");
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

    @GetMapping("/selectLocus/{vin}/{imitatePositionId}")
    public Response selectLocus(@PathVariable(value = "vin") String vin,
                                @PathVariable(value = "imitatePositionId") Long imitatePositionId){
        String positionStr = positionMapper.selectPositionByPositId(imitatePositionId);
        List<String> strings = JSONObject.parseArray(positionStr, String.class);
        String key = vin+"-"+imitatePositionId;
        redisTemplate.delete(key);
        ListOperations<String, String> operations = redisTemplate.opsForList();
        operations.leftPushAll(key,strings.toArray(new String[]{}));
        return Response.success();
    }

}



