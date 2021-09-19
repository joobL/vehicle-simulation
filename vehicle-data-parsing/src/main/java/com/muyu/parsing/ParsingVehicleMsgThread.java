package com.muyu.parsing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.muyu.annotations.KeyAnn;
import com.muyu.common.Config;
import com.muyu.pojo.VehicleData;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.awt.geom.Point2D;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        parsingVehicleFence();
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

    private void parsingVehicleFence() {
        // 检测是否含有围栏信息
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String fenceId = hashOperations.get(Config.VEHICLE_FENCE_BIND, vehicleData.getVin());
        if (StringUtils.isNotEmpty(fenceId)){
            String fencePathList = hashOperations.get(Config.VEHICLE_FENCE_INFO, fenceId);
            List<List> jsonArray = JSONObject.parseArray(fencePathList,List.class);
            List<Point2D.Double> doubleList = new ArrayList<>();
            jsonArray.forEach(positionJsonArray -> {
                doubleList.add(new Point2D.Double(
                        new BigDecimal(positionJsonArray.get(0).toString()).doubleValue(),
                        new BigDecimal(positionJsonArray.get(1).toString()).doubleValue()));
            });
//            List<Point2D.Double> doubleList = JSONObject.parseArray(hashOperations.get(Config.VEHICLE_FENCE_INFO, fenceId),Point2D.Double.class);
            // 判断围栏是否有效
            if (doubleList != null && doubleList.size() > 0){
                Point2D.Double aDouble = new Point2D.Double(
                        new BigDecimal(vehicleData.getLongitude()).doubleValue(),
                        new BigDecimal(vehicleData.getLatitude()).doubleValue());
                boolean ptInPoly = isPtInPoly(aDouble, doubleList);
                String fenceType = hashOperations.get(Config.VEHICLE_FENCE_TYPE, fenceId);
                if ("out".equals(fenceType)){
                    if (!ptInPoly){
                        // 获取围栏名称
                        String fenceName = hashOperations.get(Config.VEHICLE_FENCE_TYPE, fenceId + "name");
                        log.info("VIN:{}，触发驶离报警",vehicleData.getVin());
                        ((ListOperations<String, String>)redisTemplate.opsForList()).leftPush(Config.VEHICLE_FAULT_LOG,vehicleData.getVin()+",车辆驶离["+fenceName+"]围栏");
                    }
                }else if ("in".equals(fenceType)){
                    if (ptInPoly){
                        String fenceName = hashOperations.get(Config.VEHICLE_FENCE_TYPE, fenceId + "name");
                        log.info("VIN:{}，触发驶入报警",vehicleData.getVin());
                        ((ListOperations<String, String>)redisTemplate.opsForList()).leftPush(Config.VEHICLE_FAULT_LOG,vehicleData.getVin()+",车辆驶入["+fenceName+"]围栏");
                    }
                }
            }
        }
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

    /**
     * 判断点是否在多边形内
     * @param point 检测点
     * @param pts   多边形的顶点
     * @return      点在多边形内返回true,否则返回false
     *
     * 解法思路：设要检测的点为P点。用P点连接多边形各顶点，假如P点在多边形以内，则顶点与P组成的三角形正好填充此多边形。反之，则不能。此时使用多边形面积计算公式，使P点作为参考点（a,b,c,.......,n为多边形定点）则有：
     *
     *    S = 0.5 * ( (a.x-p.x)*(a.y-p.y)-(b.x-p.x)*(b.y-p.y) +(b.x-p.x)*(b.y-p.y)-(c.x-p.x)*(c.y-p.y) +......... + (n.x-p.x)*(n.y-p.y)-(a.x-p.x)*(a.y-p.y) )
     *
     *    如果P点不在多边形以内，则每个三角形的相对面积符号不会一致。由于0.5并不会影响我们的运算结果   所以只需要取每个三角形面积的符号(a.x-p.x)*(a.y-p.y)-(b.x-p.x)*(b.y-p.y)  如果一致则在多边形以内，反之则不在多边形以内。
     */
    public static boolean isPtInPoly(Point2D.Double point, List<Point2D.Double> pts){
        int N = pts.size();
        //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        boolean boundOrVertex = true;
        int intersectCount = 0;
        //浮点类型计算时候与0比较时候的容差
        double precision = 2e-10;
        Point2D.Double p1, p2;
        //当前点
        Point2D.Double p = point;

        p1 = pts.get(0);
        for(int i = 1; i <= N; ++i){
            if(p.equals(p1)){
                return boundOrVertex;
            }
            p2 = pts.get(i % N);
            if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){
                p1 = p2;
                continue;
            }

            if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){
                if(p.y <= Math.max(p1.y, p2.y)){
                    if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){
                        return boundOrVertex;
                    }

                    if(p1.y == p2.y){
                        if(p1.y == p.y){
                            return boundOrVertex;
                        }else{
                            ++intersectCount;
                        }
                    }else{
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;
                        if(Math.abs(p.y - xinters) < precision){
                            return boundOrVertex;
                        }

                        if(p.y < xinters){
                            ++intersectCount;
                        }
                    }
                }
            }else{
                if(p.x == p2.x && p.y <= p2.y){
                    Point2D.Double p3 = pts.get((i+1) % N);
                    if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){
                        ++intersectCount;
                    }else{
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;
        }

        if(intersectCount % 2 == 0){
            return false;
        } else {
            return true;
        }

    }
}
