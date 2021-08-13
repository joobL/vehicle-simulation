package com.muyu.log;

import com.muyu.common.Config;
import com.muyu.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 牧鱼
 * @Classname VehicleLog
 * @Description TODO
 * @Date 2021/8/10
 */
public class VehicleLog {

    /**
     * 车辆集合 标识车辆是否在线
     */
    private static Map<String,Integer> vehicleVinMap = new HashMap<>();

    private static Map<String, LinkedBlockingQueue<String>> vehicleVinLogQueue = new HashMap<>();

    public static String getVehicleLog(String vin){
        LinkedBlockingQueue<String> vehicleVinLog = getLogQueue(vin);
        if (vehicleVinLog != null){
            return vehicleVinLog.poll();
        }else {
            vinOnLine(vin);
        }
        return null;
    }

    /**
     * 添加车辆日志 含有校验 推荐使用
     * @param vin
     * @param log
     */
    public static void vinAddLog(String vin , String log){
        if (vehicleVinMap.get(vin) == null){
            vinOnLine(vin);
        }
        addLog(vin,log);
    }

    /**
     * 获取所有车辆
     * @return
     */
    public static Map<String, Integer> getVehicleVinMap() {
        return vehicleVinMap;
    }

    /**
     * 车辆上线
     * @param vin
     */
    public static void vinOnLine(String vin){
        if (StringUtils.isEmpty(vin)){
            throw new RuntimeException("日志初始化VIN不可为null");
        }
        vehicleVinMap.put(vin, Config.VEHICLE_STATUS_ONLINE);
        initVinLog(vin);
    }

    /**
     * 车辆离线
     * @param vin
     */
    public static void vinOffLine(String vin){
        if (StringUtils.isEmpty(vin)){
            throw new RuntimeException("日志初始化VIN不可为null");
        }
        vehicleVinMap.put(vin,Config.VEHICLE_STATUS_OFFLINE);
    }


    /**
     * 添加日志
     * @param vin
     * @param log
     */
    public static void addLog(String vin , String log){
        LinkedBlockingQueue<String> logQueue = getLogQueue(vin);
        logQueue.add(log);
    }


    /**
     * 初始化 车辆VIN日志队列
     * @param vin
     */
    public static void initVinLog(String vin){
        LinkedBlockingQueue<String> logQueue = getLogQueue(vin);
        logQueue.clear();
        vehicleVinLogQueue.put(vin,logQueue);
    }

    /***
     * 获取车辆上报日志队列
     * @param vin
     * @return
     */
    private static LinkedBlockingQueue<String> getLogQueue(String vin){
        LinkedBlockingQueue<String> logQueue = vehicleVinLogQueue.get(vin);
        if (logQueue == null){
            logQueue = new LinkedBlockingQueue<>();
        }
        return logQueue;
    }

}
