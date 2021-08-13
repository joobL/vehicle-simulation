package com.muyu.controller;

import com.alibaba.fastjson.JSONObject;
import com.muyu.common.Response;
import com.muyu.log.VehicleLog;
import com.muyu.pojo.VehicleData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @author 牧鱼
 * @Classname LogController
 * @Description TODO
 * @Date 2021/8/10
 */
@RestController
@RequestMapping("/vehicle/log")
public class LogController {

    @GetMapping("/vin")
    public Response<Map<String,Integer>> getVin(){
        return Response.success(VehicleLog.getVehicleVinMap());
    }

    @GetMapping("/{vin}")
    public Response<String> getVinLog(@PathVariable("vin") String vin){
        String vehicleLog = VehicleLog.getVehicleLog(vin);
        if (vehicleLog == null){
            return Response.success();
        }
        VehicleData vehicleData = JSONObject.parseObject(vehicleLog,VehicleData.class);
        return Response.success("获取日志成功！",vehicleData.getNettyVehicleMsgLog());
    }
}
