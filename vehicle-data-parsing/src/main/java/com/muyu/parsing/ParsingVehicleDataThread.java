package com.muyu.parsing;

import com.alibaba.fastjson.JSONObject;
import com.muyu.annotations.KeyAnn;
import com.muyu.common.Config;
import com.muyu.pojo.VehicleData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 牧鱼
 * @Classname VehicleData
 * @Description TODO
 * @Date 2021/9/17
 */
public class ParsingVehicleDataThread implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(ParsingVehicleDataThread.class);

    private final RedisTemplate<String , ? extends Object> redisTemplate;

    private final VehicleData vehicleData;

    public ParsingVehicleDataThread(RedisTemplate<String, ? extends Object> redisTemplate, VehicleData vehicleData) {
        this.redisTemplate = redisTemplate;
        this.vehicleData = vehicleData;
    }


    @Override
    public void run() {
        parsingVehicleData();
    }


    public void parsingVehicleData(){
        String vin = vehicleData.getVin();
        SetOperations<String, String> vehicleIsMsg = (SetOperations<String, String>) redisTemplate.opsForSet();
        if (vehicleIsMsg.isMember(Config.VEHICLE_IS_MSG,vin)){
            log.info("VIN：{}，进行分流",vin);
            ListOperations<String, String> vinAisle = (ListOperations<String, String>) redisTemplate.opsForList();
            vinAisle.leftPush(Config.VEHICLE_IS_MSG+":"+vin, JSONObject.toJSONString(getVehicleData()));
        }
    }

    public Map<String,String> getVehicleData(){
        Map<String,String> dataMap = new HashMap<String, String>(){{
            put("latitude",vehicleData.getLatitude());
            put("longitude",vehicleData.getLongitude());
            put("speed",vehicleData.getSpeed());
            put("voltage",vehicleData.getSingleBatteryMinVoltage());
            put("temperature",vehicleData.getSingleBatteryMinTemperature());
        }};
        Class<?> clazz = VehicleData.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            KeyAnn annotation = field.getAnnotation(KeyAnn.class);
            if (annotation != null){
                String key = annotation.key();
                if (StringUtils.isNotEmpty(key)){
                    String unit = annotation.unit();
                    try {
                        field.setAccessible(true);
                        Object invoke = field.get(vehicleData);
                        dataMap.put(key,invoke != null ? (invoke.toString() + unit) : "-");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
        return dataMap;
    }
    public static Map<String,String> getVehicleData(VehicleData vehicleData){
        return new ParsingVehicleDataThread(null , vehicleData).getVehicleData();
    }
}
