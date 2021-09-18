package com.muyu.parsing;

import com.alibaba.fastjson.JSONObject;
import com.muyu.annotations.KeyAnn;
import com.muyu.common.Config;
import com.muyu.pojo.VehicleData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 牧鱼
 * @Classname VehicleData
 * @Description TODO
 * @Date 2021/9/17
 */
public class ParsingVehicleMsgThread implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(ParsingVehicleMsgThread.class);

    private final RedisTemplate<String , ? extends Object> redisTemplate;

    private final VehicleData vehicleData;

    public ParsingVehicleMsgThread(RedisTemplate<String, ? extends Object> redisTemplate, VehicleData vehicleData) {
        this.redisTemplate = redisTemplate;
        this.vehicleData = vehicleData;
    }


    @Override
    public void run() {
        parsingVehicleData();
    }


    public void parsingVehicleData(){

        /** 车辆接触状态MSG */
        String vehicleStatusMsg = vehicleData.getVehicleStatusMsg();

        /** 车辆零配件报文 */
        String smartHardwareMsg = vehicleData.getSmartHardwareMsg();

        /** 车辆电池报文 */
        String batteryMsg = vehicleData.getBatteryMsg();

        parsingVehicleStatusMsg(vehicleStatusMsg);
        parsingVehiclePartsMsg(smartHardwareMsg);
        parsingVehicleBatteryMsg(batteryMsg);
    }

    private void parsingVehicleBatteryMsg(String batteryMsg) {
        batteryMsg = batteryMsg.replace(Config.VEHICLE_BATTERY_FAULT,"").replace("7X","");
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        ListOperations<String, String> listOperations = (ListOperations<String, String>) redisTemplate.opsForList();
        String[] batteryMsgSplit = batteryMsg.split(" ");
        var length = batteryMsgSplit.length;
        for (int i = 0; i < length; i++) {
            String key = i + ":" + batteryMsgSplit[i];
            String value = hashOperations.get(Config.VEHICLE_BATTERY_FAULT, key);
            if (value != null){
                log.warn("车辆：{}，发生故障，故障部位：{}",this.vehicleData.getVin(),value);
                listOperations.leftPush(Config.VEHICLE_FAULT_LOG , this.vehicleData.getVin()+","+value);
            }
        }
    }

    private void parsingVehiclePartsMsg(String smartHardwareMsg) {
        smartHardwareMsg = smartHardwareMsg.replace(Config.VEHICLE_PARTS_FAULT,"").replace("7X","");
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        ListOperations<String, String> listOperations = (ListOperations<String, String>) redisTemplate.opsForList();
        String[] smartHardwareMsgSplit = smartHardwareMsg.split(" ");
        var length = smartHardwareMsgSplit.length;
        for (int i = 0; i < length; i++) {
            String key = i + ":" + smartHardwareMsgSplit[i];
            String value = hashOperations.get(Config.VEHICLE_PARTS_FAULT, key);
            if (value != null){
                listOperations.leftPush(Config.VEHICLE_FAULT_LOG , this.vehicleData.getVin()+","+value);
                log.warn("车辆：{}，发生故障，故障部位：{}",this.vehicleData.getVin(),value);
            }
        }
    }

    private void parsingVehicleStatusMsg(String vehicleStatusMsg) {
        vehicleStatusMsg = vehicleStatusMsg.replace(Config.VEHICLE_BASE_FAULT,"").replace("7X","");
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        ListOperations<String, String> listOperations = (ListOperations<String, String>) redisTemplate.opsForList();
        String[] vehicleStatusMsgSplit = vehicleStatusMsg.split(" ");
        var length = vehicleStatusMsgSplit.length;
        for (int i = 0; i < length; i++) {
            String key = i + ":" + vehicleStatusMsgSplit[i];
            String value = hashOperations.get(Config.VEHICLE_BASE_FAULT, key);
            if (value != null){
                listOperations.leftPush(Config.VEHICLE_FAULT_LOG , this.vehicleData.getVin()+","+value);
                log.warn("车辆：{}，发生故障，故障部位：{}",this.vehicleData.getVin(),value);
            }
        }
    }

}
