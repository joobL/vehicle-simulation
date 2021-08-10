<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>车联数据模拟</title>
</head>
<link rel="stylesheet" href="/static/bootstrap-3.4.1/css/bootstrap.css"/>
<link rel="stylesheet" href="/static/layer/mobile/need/layer.css">
<script src="/static/vehicle/js/jquery.min.js"></script>
<script src="/static/layer/layer.js"></script>
<script src="/static/bootstrap-3.4.1/js/bootstrap.js"></script>
<script src="/static/vehicle/js/muyu.js"></script>
<body>
<style>
    .divCss{
        margin: 5px 5px;
        border-radius: 5px;
    }
    .divBorderHeight{
        border: solid 1px #0081a7 ;
    }
    .vinDiv::-webkit-scrollbar {/*滚动条整体样式*/
        width:3px;    /*高宽分别对应横竖滚动条的尺寸*/
        height:1px;
    }
    .vinDiv::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
        border-radius:10px;
        -webkit-box-shadow:inset 0 0 5px rgba(0,0,0,0.2);
        background:#009dcc;
    }
    .vinDiv::-webkit-scrollbar-track {/*滚动条里面轨道*/
        -webkit-box-shadow:inset 0 0 5px rgba(0,0,0,0.2);
        border-radius:10px;
        background:#EDEDED;
    }
    .vinDiv span{
        float: left;
        margin: 5px 15px 5px 0;
        font-size: 15px;
        cursor:pointer;
    }
    .vinDiv{
        height: 820px;
        float: left;
        overflow: hidden;
        overflow-y: auto;
    }
    .col-md-2 {
        width: 16%;
    }
    .message{
        border-bottom: #0673b3 2px solid;
        border-right: #0673b3 2px solid;
        width: 33.33%;
    }
</style>
<div style="margin-right: 10px; margin-left: 10px;">
    <div class="col-md-12 divCss divBorderHeight">
        <div class="col-md-5 divCss divBorderHeight">
            <div class="form-inline">
                <div class="form-group">
                    <label for="host">地址</label>
                    <input type="text" class="form-control" name="" id="host" placeholder="IP地址" value="127.0.0.1">
                </div>
                <div class="form-group">
                    <label for="port">端口</label>
                    <input type="text" class="form-control" name="" id="port" placeholder="端口" value="8564">
                </div>
                <button type="button" class="btn btn-sm btn-default" onclick="connect()">连接</button>
                <button type="button" class="btn btn-sm btn-danger" onclick="disConnect()">关闭</button>
                <span id="connect" class="label label-success" style="display: none">已连接</span>
                <span id="disconnect" class="label label-danger">已断开</span>
            </div>
        </div>
        <div class="col-md-1 divCss divBorderHeight" style="padding: 0">
            <div class="col-md-4">
                <button type="button" class="btn btn-sm btn-default" onclick="startNettySendMsg()">上报</button>
            </div>
            <div class="col-md-4">
                <button type="button" class="btn btn-sm btn-default" onclick="stopNettyStopMsg()">停止</button>
            </div>
        </div>

        <div class="col-md-2 divCss divBorderHeight" style="padding: 7px 0">
            <span class="col-md-7">当累计上报数据量：</span>
            <span class="label label-info" id="sendSum">12312</span>
            <span id="send" class="label label-success" style="display: none">上报中</span>
            <span id="disSend" class="label label-danger">未上报</span>
        </div>
    </div>
    <form id="vehicleForm">
    <div class="col-md-12 divCss divBorderHeight" style="height: 875px;">
        <h4>上报数据模拟</h4>
        <hr style="margin-top: 10px; margin-bottom: 10px; border-top: 2px solid #080808;">
        <div class="col-md-12">
            <div class="vinDiv" style="border-right: #0673b3 2px solid; width: 15%;">
                <div class="col-sm-12">
                    <div class="form-group">
                        <label for="vin">VIN</label>
                        <input type="text" class="form-control" name="vin" id="vin" placeholder="VIN" maxlength="17">
                    </div>
                    <div class="form-group">
                        <label for="drivingRoute">选择路线</label>
                        <select type="text" class="form-control" name="drivingRoute" id="drivingRoute">
                            <option value="-">请选择行驶路线</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="speed">车速</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="speed" id="speed" placeholder="车速">
                            <div class="input-group-addon">KM/H</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mileage">里程</label>

                        <div class="input-group">
                            <input type="text" class="form-control" name="mileage" id="mileage" placeholder="里程">
                            <div class="input-group-addon">KM</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="voltage">总电压</label>

                        <div class="input-group">
                            <input type="text" class="form-control" name="voltage" id="voltage" placeholder="总电压">
                            <div class="input-group-addon">V</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="current">总电流</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="current" id="current" placeholder="总电流">
                            <div class="input-group-addon">A</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="resistance">绝缘电阻</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="resistance" id="resistance" placeholder="绝缘电阻">
                            <div class="input-group-addon">Ω</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="gear">档位</label>
                        <select class="form-control" name="gear" id="gear">
                            <option value="P" selected>驻车</option>
                            <option value="R">倒车</option>
                            <option value="N">空挡</option>
                            <option value="D">正常模式</option>
                            <option value="S">运动模式</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="accelerationPedal">加速踏板行程值</label>
                        <input type="text" class="form-control" name="accelerationPedal" id="accelerationPedal" placeholder="加速踏板行程值">
                    </div>
                    <div class="form-group">
                        <label for="brakePedal">制动踏板行程值</label>
                        <input type="text" class="form-control" name="brakePedal" id="brakePedal" placeholder="制动踏板行程值">
                    </div>
                    <div class="form-group">
                        <label for="fuelConsumptionRate">燃料消耗率</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="fuelConsumptionRate" id="fuelConsumptionRate" placeholder="燃料消耗率">
                            <div class="input-group-addon">L/百公里</div>
                        </div>
                    </div>

                </div>
            </div>

            <div class="vinDiv" style="border-right: #0673b3 2px solid; width: 15%;">
                <div class="col-sm-12">
                    <div class="form-group">
                        <label for="motorControllerTemperature">电机控制器温度</label>
                        <input type="text" class="form-control" name="motorControllerTemperature" id="motorControllerTemperature" placeholder="电机控制器温度">
                    </div>
                    <div class="form-group">
                        <label for="motorSpeed">电机转速</label>
                        <input type="text" class="form-control" name="motorSpeed" id="motorSpeed" placeholder="电机转速">
                    </div>
                    <div class="form-group">
                        <label for="motorTorque">电机转矩</label>
                        <input type="text" class="form-control" name="motorTorque" id="motorTorque" placeholder="电机转矩">
                    </div>
                    <div class="form-group">
                        <label for="motorTemperature">电机温度</label>
                        <input type="text" class="form-control" name="motorTemperature" id="motorTemperature" placeholder="电机温度">
                    </div>
                    <div class="form-group">
                        <label for="motorVoltage">电机电压</label>
                        <input type="text" class="form-control" name="motorVoltage" id="motorVoltage" placeholder="电机电压">
                    </div>
                    <div class="form-group">
                        <label for="motorCurrent">电机电流</label>
                        <input type="text" class="form-control" name="motorCurrent" id="motorCurrent" placeholder="电机电流">
                    </div>
                </div>
            </div>
            <div class="vinDiv" style="border-right: #0673b3 2px solid; width: 15%;">
                <div class="col-sm-12">
                    <div class="form-group">
                        <label for="remainingBattery">动力电池剩余电量SOC</label>

                        <div class="input-group">
                            <input type="text" class="form-control" name="remainingBattery" id="remainingBattery" placeholder="动力电池剩余电量SOC">
                            <div class="input-group-addon">%</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="maximumFeedbackPower">当前状态允许的最大反馈功率</label>
                        <input type="text" class="form-control" name="maximumFeedbackPower" id="maximumFeedbackPower" placeholder="当前状态允许的最大反馈功率">
                    </div>
                    <div class="form-group">
                        <label for="maximumDischargePower">当前状态允许最大放电功率</label>
                        <input type="text" class="form-control" name="maximumDischargePower" id="maximumDischargePower" placeholder="当前状态允许最大放电功率">
                    </div>
                    <div class="form-group">
                        <label for="selfCheckCounter">BMS自检计数器</label>
                        <input type="text" class="form-control" name="selfCheckCounter" id="selfCheckCounter" placeholder="BMS自检计数器">
                    </div>
                    <div class="form-group">
                        <label for="totalBatteryCurrent">动力电池充放电电流</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="totalBatteryCurrent" id="totalBatteryCurrent" placeholder="动力电池充放电电流">
                            <div class="input-group-addon">A</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="totalBatteryVoltage">动力电池负载端总电压V3</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="totalBatteryVoltage" id="totalBatteryVoltage" placeholder="动力电池负载端总电压V3">
                            <div class="input-group-addon">V</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="singleBatteryMaxVoltage">单次最大电压</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="singleBatteryMaxVoltage" id="singleBatteryMaxVoltage" placeholder="单次最大电压">
                            <div class="input-group-addon">V</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="singleBatteryMinVoltage">单体电池最低电压</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="singleBatteryMinVoltage" id="singleBatteryMinVoltage" placeholder="单体电池最低电压">
                            <div class="input-group-addon">V</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="singleBatteryMaxTemperature">单体电池最高温度</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="singleBatteryMaxTemperature" id="singleBatteryMaxTemperature" placeholder="单体电池最高温度">
                            <div class="input-group-addon">℃</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="singleBatteryMinTemperature">单体电池最低温度</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="singleBatteryMinTemperature" id="singleBatteryMinTemperature" placeholder="单体电池最低温度">
                            <div class="input-group-addon">℃</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="availableBatteryCapacity">动力电池可用容量</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="availableBatteryCapacity" id="availableBatteryCapacity" placeholder="动力电池可用容量">
                            <div class="input-group-addon">%</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="vinDiv" style="border-right: #0673b3 2px solid; width: 55%;">
                <div class="vinDiv message" style="height: 495px;">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label style="cursor: default;">车辆状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="vehicleStatus" id="vehicleStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="vehicleStatus" id="vehicleStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">充电状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="chargingStatus" id="chargingStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="chargingStatus" id="chargingStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">运行状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="operatingStatus" id="operatingStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="operatingStatus" id="operatingStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">SOC：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="socStatus" id="socStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="socStatus" id="socStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">可充电储能装置工作状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="chargingEnergyStorageStatus" id="chargingEnergyStorageStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="chargingEnergyStorageStatus" id="chargingEnergyStorageStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">驱动电机状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="driveMotorStatus" id="driveMotorStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="driveMotorStatus" id="driveMotorStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">定位是否有效：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="positionStatus" id="positionStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="positionStatus" id="positionStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="vinDiv message" style="height: 495px;">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label style="cursor: default;">EAS(汽车防盗系统)状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="easStatus" id="easStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="easStatus" id="easStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">PTC(电动加热器)状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="ptcStatus" id="ptcStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="ptcStatus" id="ptcStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">EPS(电动助力系统)状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="epsStatus" id="epsStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="epsStatus" id="epsStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">ABS(防抱死)状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="absStatus" id="absStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="absStatus" id="absStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">MCU(电机/逆变器)状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="mcuStatus" id="mcuStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="mcuStatus" id="mcuStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="vinDiv message" style="height: 495px;">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label style="cursor: default;">动力电池加热状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="heatingStatus" id="heatingStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="heatingStatus" id="heatingStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">动力电池当前状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="batteryStatus" id="batteryStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="batteryStatus" id="batteryStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">动力电池保温状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="batteryInsulationStatus" id="batteryInsulationStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="batteryInsulationStatus" id="batteryInsulationStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">DCDC(电力交换系统)状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="dcdcStatus" id="dcdcStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="dcdcStatus" id="dcdcStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label style="cursor: default;">CHG(充电机)状态：</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="chgStatus" id="chgStatusNormal" value="1" checked>
                                    正常
                                </label>
                                <label>
                                    <input type="radio" name="chgStatus" id="chgStatusAbnormal" value="0">
                                    异常
                                </label>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="vinDiv" id="vehicleMsgLog" style="height :325px ;width: 100%; padding: 0 15px;">

                </div>
            </div>
        </div>
    </div>
    </form>
</div>
</body>
<script>
    var nettyLogTime = null;
    var nettyMsgTime = null;
    /**
     * 连接服务器
     */
    function connect(){
        $.muyuSend.post("/nettyClient/initNettyClient",{"host": $("#host").val() , "port" : $("#port").val()},function (response) {
            if (response.code == 100){
                connectEnd();
            }else {
                $.modal.msgWarning(response.msg);
            }
        })
    }

    /**
     * 断开服务器
     */
    function disConnect(){
        $.muyuSend.get("/nettyClient/nettyDestroy",undefined,function (response) {
            if (response.code == 100){
                disconnect();
            }else {
                disconnect();
                $.modal.msgWarning(response.msg);
            }
        })
    }

    function connectEnd() {
        $("#connect").css("display","");
        $("#disconnect").css("display","none");
    }
    function disconnect() {
        $("#connect").css("display","none");
        $("#disconnect").css("display","");
    }

    $(function () {
        $.muyuSend.get("/nettyClient/status",undefined,function (response) {
            if (response.code == 100){
                connectEnd();
            }else {
                disconnect();
            }
        })
        startNettyLog();
    })
    function addLog(msg){
        $("#vehicleMsgLog").append(msg);
    }
    function startNettySendMsg(){
        nettyMsgTime = setInterval("sendNettySendMsg()", 1000);
    }
    function stopNettyStopMsg(){
        clearInterval(nettyMsgTime);
        nettyMsgTime = null;
    }
    function sendNettySendMsg(){
        $.muyuSend.post("/nettyClient/nettySendMsg",getFormToMap("vehicleForm"),function (response) {
            if (response.code != 100){
                $.modal.msgWarning(response.msg);
                stopNettyStopMsg();
            }
        })
    }

    function startNettyLog(){
        nettyLogTime = setInterval("getNettyLog()", 1000);
    }
    function stopNettyLog(){
        clearInterval(nettyLogTime);
        nettyLogTime = null;
    }
    function getNettyLog(){
        $.muyuSend.get("/netty/log",undefined,function (response) {
            if (response.code == 100){
                addLog(response.msg);
            }else {
                $.modal.msgWarning(response.msg);
            }
        })
    }
</script>
</html>
