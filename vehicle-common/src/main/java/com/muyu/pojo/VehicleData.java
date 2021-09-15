package com.muyu.pojo;

import com.muyu.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 牧鱼
 * @Classname VehicleData
 * @Description 车辆模拟数据对象
 * @Date 2021/8/5
 */
public class VehicleData {
    /**
     * VIN
     */
    private String vin;
    /**
     * 行驶路线
     */
    private String drivingRoute;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 维度
     */
    private String latitude;

    /**
     * 速度
     */
    private String speed;

    /**
     * 里程
     */
    private String mileage;

    /**
     * 总电压
     */
    private String voltage;

    /**
     * 总电流
     */
    private String current;

    /**
     * 绝缘电阻
     */
    private String resistance;

    /**
     * 档位
     */
    private String gear;

    /**
     * 加速踏板行程值
     */
    private String accelerationPedal;

    /**
     * 制动踏板行程值
     */
    private String brakePedal;

    /**
     * 燃料消耗率
     */
    private String fuelConsumptionRate;

    /**
     * 电机控制器温度
     */
    private String motorControllerTemperature;

    /**
     * 电机转速
     */
    private String motorSpeed;

    /**
     * 电机转矩
     */
    private String motorTorque;

    /**
     * 电机温度
     */
    private String motorTemperature;

    /**
     * 电机电压
     */
    private String motorVoltage;

    /**
     * 电机电流
     */
    private String motorCurrent;

    /**
     * 动力电池剩余电量SOC
     */
    private String remainingBattery;

    /**
     * 当前状态允许的最大反馈功率
     */
    private String maximumFeedbackPower;

    /**
     * 当前状态允许最大放电功率
     */
    private String maximumDischargePower;

    /**
     * BMS自检计数器
     */
    private String selfCheckCounter;

    /**
     * 动力电池充放电电流
     */
    private String totalBatteryCurrent;

    /**
     * 动力电池负载端总电压V3
     */
    private String totalBatteryVoltage;

    /**
     * 单次最大电压
     */
    private String singleBatteryMaxVoltage;

    /**
     * 单体电池最低电压
     */
    private String singleBatteryMinVoltage;

    /**
     * 单体电池最高温度
     */
    private String singleBatteryMaxTemperature;

    /**
     * 单体电池最低温度
     */
    private String singleBatteryMinTemperature;

    /**
     * 动力电池可用容量
     */
    private String availableBatteryCapacity;

    /**
     * 车辆状态
     */
    private int vehicleStatus;

    /**
     * 充电状态
     */
    private int chargingStatus;

    /**
     * 运行状态
     */
    private int operatingStatus;

    /**
     * SOC
     */
    private int socStatus;

    /**
     * 可充电储能装置工作状态
     */
    private int chargingEnergyStorageStatus;

    /**
     * 驱动电机状态
     */
    private int driveMotorStatus;

    /**
     * 定位是否有效
     */
    private int positionStatus;

    /**
     * EAS(汽车防盗系统)状态
     */
    private int easStatus;

    /**
     * PTC(电动加热器)状态
     */
    private int ptcStatus;

    /**
     * EPS(电动助力系统)状态
     */
    private int epsStatus;

    /**
     * ABS(防抱死)状态
     */
    private int absStatus;

    /**
     * MCU(电机/逆变器)状态
     */
    private int mcuStatus;

    /**
     * 动力电池加热状态
     */
    private int heatingStatus;

    /**
     * 动力电池当前状态
     */
    private int batteryStatus;

    /**
     * 动力电池保温状态
     */
    private int batteryInsulationStatus;

    /**
     * DCDC(电力交换系统)状态
     */
    private int dcdcStatus;

    /**
     * CHG(充电机)状态
     */
    private int chgStatus;

    /**
     * 车辆状态 报文
     */
    private String vehicleStatusMsg;
    /**
     * 智能硬件 报文
     */
    private String smartHardwareMsg;
    /**
     * 电池报文
     */
    private String batteryMsg;

    public String getVehicleStatusMsg() {
        StringBuilder vehicleStatusMsgSb = new StringBuilder("7X");
        vehicleStatusMsgSb.append(" 0").append(vehicleStatus);
        vehicleStatusMsgSb.append(" 0").append(chargingStatus);
        vehicleStatusMsgSb.append(" 0").append(operatingStatus);
        vehicleStatusMsgSb.append(" 0").append(socStatus);
        vehicleStatusMsgSb.append(" 0").append(chargingEnergyStorageStatus);
        vehicleStatusMsgSb.append(" 0").append(driveMotorStatus);
        vehicleStatusMsgSb.append(" 0").append(positionStatus);
        vehicleStatusMsgSb.append("7X");
        vehicleStatusMsg = vehicleStatusMsgSb.toString();
        return vehicleStatusMsg;
    }

    public String getSmartHardwareMsg() {
        StringBuilder smartHardwareMsgSb = new StringBuilder("7X");
        smartHardwareMsgSb.append(" 0").append(easStatus);
        smartHardwareMsgSb.append(" 0").append(ptcStatus);
        smartHardwareMsgSb.append(" 0").append(epsStatus);
        smartHardwareMsgSb.append(" 0").append(absStatus);
        smartHardwareMsgSb.append(" 0").append(mcuStatus);
        smartHardwareMsgSb.append("7X");
        smartHardwareMsg = smartHardwareMsgSb.toString();
        return smartHardwareMsg;
    }

    public String getBatteryMsg() {
        StringBuilder batteryMsgSb = new StringBuilder("7X");
        batteryMsgSb.append(" 0").append(heatingStatus);
        batteryMsgSb.append(" 0").append(batteryStatus);
        batteryMsgSb.append(" 0").append(batteryInsulationStatus);
        batteryMsgSb.append(" 0").append(dcdcStatus);
        batteryMsgSb.append(" 0").append(chgStatus);
        batteryMsgSb.append(" 7X");
        batteryMsg = batteryMsgSb.toString();
        return batteryMsg;
    }

    public String getNettyVehicleMsgLog(){
        StringBuilder vehicleLog = new StringBuilder("<p>");
        vehicleLog.append("车辆：").append(this.vin);
        vehicleLog.append("上报数据：{").append("<br>上报时间：").append(DateUtils.getNow());
        vehicleLog.append("    经度："+this.longitude+"，维度："+this.latitude).append("<br>车辆报文信息：");
        vehicleLog.append("车辆状态：[").append(getVehicleStatusMsg()).append("]    ");
        vehicleLog.append("智能硬件：[").append(getSmartHardwareMsg()).append("]    ");
        vehicleLog.append("电池状态：[").append(getBatteryMsg()).append("]<br>");
        vehicleLog.append("}</p> <hr style='border-top:3px solid #0071fd'>");
        return vehicleLog.toString();
    }

    public String getVehicleMsgLog(){
        StringBuilder vehicleLog = new StringBuilder("<p>");
        vehicleLog.append(DateUtils.getNow()).append("&nbsp;&nbsp;&nbsp;");
        vehicleLog.append("车辆状态报文：[").append(getVehicleStatusMsg()).append("]    ");
        vehicleLog.append("智能硬件报文：[").append(getSmartHardwareMsg()).append("]    ");
        vehicleLog.append("电池状态报文：[").append(getBatteryMsg()).append("]");
        vehicleLog.append("</p>");
        return vehicleLog.toString();
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getDrivingRoute() {
        return drivingRoute;
    }

    public void setDrivingRoute(String drivingRoute) {
        this.drivingRoute = drivingRoute;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getResistance() {
        return resistance;
    }

    public void setResistance(String resistance) {
        this.resistance = resistance;
    }

    public String getGear() {
        return gear;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }

    public String getAccelerationPedal() {
        return accelerationPedal;
    }

    public void setAccelerationPedal(String accelerationPedal) {
        this.accelerationPedal = accelerationPedal;
    }

    public String getBrakePedal() {
        return brakePedal;
    }

    public void setBrakePedal(String brakePedal) {
        this.brakePedal = brakePedal;
    }

    public String getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    public void setFuelConsumptionRate(String fuelConsumptionRate) {
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    public String getMotorControllerTemperature() {
        return motorControllerTemperature;
    }

    public void setMotorControllerTemperature(String motorControllerTemperature) {
        this.motorControllerTemperature = motorControllerTemperature;
    }

    public String getMotorSpeed() {
        return motorSpeed;
    }

    public void setMotorSpeed(String motorSpeed) {
        this.motorSpeed = motorSpeed;
    }

    public String getMotorTorque() {
        return motorTorque;
    }

    public void setMotorTorque(String motorTorque) {
        this.motorTorque = motorTorque;
    }

    public String getMotorTemperature() {
        return motorTemperature;
    }

    public void setMotorTemperature(String motorTemperature) {
        this.motorTemperature = motorTemperature;
    }

    public String getMotorVoltage() {
        return motorVoltage;
    }

    public void setMotorVoltage(String motorVoltage) {
        this.motorVoltage = motorVoltage;
    }

    public String getMotorCurrent() {
        return motorCurrent;
    }

    public void setMotorCurrent(String motorCurrent) {
        this.motorCurrent = motorCurrent;
    }

    public String getRemainingBattery() {
        return remainingBattery;
    }

    public void setRemainingBattery(String remainingBattery) {
        this.remainingBattery = remainingBattery;
    }

    public String getMaximumFeedbackPower() {
        return maximumFeedbackPower;
    }

    public void setMaximumFeedbackPower(String maximumFeedbackPower) {
        this.maximumFeedbackPower = maximumFeedbackPower;
    }

    public String getMaximumDischargePower() {
        return maximumDischargePower;
    }

    public void setMaximumDischargePower(String maximumDischargePower) {
        this.maximumDischargePower = maximumDischargePower;
    }

    public String getSelfCheckCounter() {
        return selfCheckCounter;
    }

    public void setSelfCheckCounter(String selfCheckCounter) {
        this.selfCheckCounter = selfCheckCounter;
    }

    public String getTotalBatteryCurrent() {
        return totalBatteryCurrent;
    }

    public void setTotalBatteryCurrent(String totalBatteryCurrent) {
        this.totalBatteryCurrent = totalBatteryCurrent;
    }

    public String getTotalBatteryVoltage() {
        return totalBatteryVoltage;
    }

    public void setTotalBatteryVoltage(String totalBatteryVoltage) {
        this.totalBatteryVoltage = totalBatteryVoltage;
    }

    public String getSingleBatteryMaxVoltage() {
        return singleBatteryMaxVoltage;
    }

    public void setSingleBatteryMaxVoltage(String singleBatteryMaxVoltage) {
        this.singleBatteryMaxVoltage = singleBatteryMaxVoltage;
    }

    public String getSingleBatteryMinVoltage() {
        return singleBatteryMinVoltage;
    }

    public void setSingleBatteryMinVoltage(String singleBatteryMinVoltage) {
        this.singleBatteryMinVoltage = singleBatteryMinVoltage;
    }

    public String getSingleBatteryMaxTemperature() {
        return singleBatteryMaxTemperature;
    }

    public void setSingleBatteryMaxTemperature(String singleBatteryMaxTemperature) {
        this.singleBatteryMaxTemperature = singleBatteryMaxTemperature;
    }

    public String getSingleBatteryMinTemperature() {
        return singleBatteryMinTemperature;
    }

    public void setSingleBatteryMinTemperature(String singleBatteryMinTemperature) {
        this.singleBatteryMinTemperature = singleBatteryMinTemperature;
    }

    public String getAvailableBatteryCapacity() {
        return availableBatteryCapacity;
    }

    public void setAvailableBatteryCapacity(String availableBatteryCapacity) {
        this.availableBatteryCapacity = availableBatteryCapacity;
    }

    public int getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(int vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public int getChargingStatus() {
        return chargingStatus;
    }

    public void setChargingStatus(int chargingStatus) {
        this.chargingStatus = chargingStatus;
    }

    public int getOperatingStatus() {
        return operatingStatus;
    }

    public void setOperatingStatus(int operatingStatus) {
        this.operatingStatus = operatingStatus;
    }

    public int getSocStatus() {
        return socStatus;
    }

    public void setSocStatus(int socStatus) {
        this.socStatus = socStatus;
    }

    public int getChargingEnergyStorageStatus() {
        return chargingEnergyStorageStatus;
    }

    public void setChargingEnergyStorageStatus(int chargingEnergyStorageStatus) {
        this.chargingEnergyStorageStatus = chargingEnergyStorageStatus;
    }

    public int getDriveMotorStatus() {
        return driveMotorStatus;
    }

    public void setDriveMotorStatus(int driveMotorStatus) {
        this.driveMotorStatus = driveMotorStatus;
    }

    public int getPositionStatus() {
        return positionStatus;
    }

    public void setPositionStatus(int positionStatus) {
        this.positionStatus = positionStatus;
    }

    public int getEasStatus() {
        return easStatus;
    }

    public void setEasStatus(int easStatus) {
        this.easStatus = easStatus;
    }

    public int getPtcStatus() {
        return ptcStatus;
    }

    public void setPtcStatus(int ptcStatus) {
        this.ptcStatus = ptcStatus;
    }

    public int getEpsStatus() {
        return epsStatus;
    }

    public void setEpsStatus(int epsStatus) {
        this.epsStatus = epsStatus;
    }

    public int getAbsStatus() {
        return absStatus;
    }

    public void setAbsStatus(int absStatus) {
        this.absStatus = absStatus;
    }

    public int getMcuStatus() {
        return mcuStatus;
    }

    public void setMcuStatus(int mcuStatus) {
        this.mcuStatus = mcuStatus;
    }

    public int getHeatingStatus() {
        return heatingStatus;
    }

    public void setHeatingStatus(int heatingStatus) {
        this.heatingStatus = heatingStatus;
    }

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(int batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public int getBatteryInsulationStatus() {
        return batteryInsulationStatus;
    }

    public void setBatteryInsulationStatus(int batteryInsulationStatus) {
        this.batteryInsulationStatus = batteryInsulationStatus;
    }

    public int getDcdcStatus() {
        return dcdcStatus;
    }

    public void setDcdcStatus(int dcdcStatus) {
        this.dcdcStatus = dcdcStatus;
    }

    public int getChgStatus() {
        return chgStatus;
    }

    public void setChgStatus(int chgStatus) {
        this.chgStatus = chgStatus;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "VehicleData{" +
                "vin='" + vin + '\'' +
                ", drivingRoute='" + drivingRoute + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", speed='" + speed + '\'' +
                ", mileage='" + mileage + '\'' +
                ", voltage='" + voltage + '\'' +
                ", current='" + current + '\'' +
                ", resistance='" + resistance + '\'' +
                ", gear='" + gear + '\'' +
                ", accelerationPedal='" + accelerationPedal + '\'' +
                ", brakePedal='" + brakePedal + '\'' +
                ", fuelConsumptionRate='" + fuelConsumptionRate + '\'' +
                ", motorControllerTemperature='" + motorControllerTemperature + '\'' +
                ", motorSpeed='" + motorSpeed + '\'' +
                ", motorTorque='" + motorTorque + '\'' +
                ", motorTemperature='" + motorTemperature + '\'' +
                ", motorVoltage='" + motorVoltage + '\'' +
                ", motorCurrent='" + motorCurrent + '\'' +
                ", remainingBattery='" + remainingBattery + '\'' +
                ", maximumFeedbackPower='" + maximumFeedbackPower + '\'' +
                ", maximumDischargePower='" + maximumDischargePower + '\'' +
                ", selfCheckCounter='" + selfCheckCounter + '\'' +
                ", totalBatteryCurrent='" + totalBatteryCurrent + '\'' +
                ", totalBatteryVoltage='" + totalBatteryVoltage + '\'' +
                ", singleBatteryMaxVoltage='" + singleBatteryMaxVoltage + '\'' +
                ", singleBatteryMinVoltage='" + singleBatteryMinVoltage + '\'' +
                ", singleBatteryMaxTemperature='" + singleBatteryMaxTemperature + '\'' +
                ", singleBatteryMinTemperature='" + singleBatteryMinTemperature + '\'' +
                ", availableBatteryCapacity='" + availableBatteryCapacity + '\'' +
                ", vehicleStatus=" + vehicleStatus +
                ", chargingStatus=" + chargingStatus +
                ", operatingStatus=" + operatingStatus +
                ", socStatus=" + socStatus +
                ", chargingEnergyStorageStatus=" + chargingEnergyStorageStatus +
                ", driveMotorStatus=" + driveMotorStatus +
                ", positionStatus=" + positionStatus +
                ", easStatus=" + easStatus +
                ", ptcStatus=" + ptcStatus +
                ", epsStatus=" + epsStatus +
                ", absStatus=" + absStatus +
                ", mcuStatus=" + mcuStatus +
                ", heatingStatus=" + heatingStatus +
                ", batteryStatus=" + batteryStatus +
                ", batteryInsulationStatus=" + batteryInsulationStatus +
                ", dcdcStatus=" + dcdcStatus +
                ", chgStatus=" + chgStatus +
                ", vehicleStatusMsg='" + vehicleStatusMsg + '\'' +
                ", smartHardwareMsg='" + smartHardwareMsg + '\'' +
                ", batteryMsg='" + batteryMsg + '\'' +
                '}';
    }

    public Map<String, String> getBaseData() {
        Map<String, String> dataBaseMap = new HashMap<>();
        dataBaseMap.put("vin",vin);
        dataBaseMap.put("drivingRoute",drivingRoute);
        dataBaseMap.put("speed",speed);
        dataBaseMap.put("mileage",mileage);
        dataBaseMap.put("voltage",voltage);
        dataBaseMap.put("current",current);
        dataBaseMap.put("resistance",resistance);
        dataBaseMap.put("gear",gear);
        dataBaseMap.put("accelerationPedal",accelerationPedal);
        dataBaseMap.put("brakePedal",brakePedal);
        dataBaseMap.put("fuelConsumptionRate",fuelConsumptionRate);
        dataBaseMap.put("motorControllerTemperature",motorControllerTemperature);
        dataBaseMap.put("motorSpeed",motorSpeed);
        dataBaseMap.put("motorTorque",motorTorque);
        dataBaseMap.put("motorTemperature",motorTemperature);
        dataBaseMap.put("motorVoltage",motorVoltage);
        dataBaseMap.put("motorCurrent",motorCurrent);
        return dataBaseMap;
    }
}
