<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Netty监控台</title>
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
        height: 35px;
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
    .vinDiv{
        height: 750px;
        float: left;
        overflow: hidden;
        overflow-y: auto;
    }
    .col-md-2 {
        width: 16%;
    }
</style>
<div style="margin-right: 10px; margin-left: 10px;">
    <div class="col-md-12 divCss divBorderHeight" style="height: 47px;">
        <div class="col-md-2 divCss divBorderHeight" style="padding: 7px 0">
            <span class="col-md-7">Netty服务器状态：</span>
            <span class="label label-success">正常</span>
        </div>
        <div class="col-md-2 divCss divBorderHeight" style="padding: 7px 0">
            <span class="col-md-7">netty服务器端口：</span>
            <span class="label label-primary">8564</span>
        </div>
        <div class="col-md-2 divCss divBorderHeight" style="padding: 7px 0">
            <span class="col-md-7">kafka服务器状态：</span>
            <span class="label label-warning">异常</span>
        </div>
        <div class="col-md-2 divCss divBorderHeight" style="padding: 7px 0">
            <span class="col-md-7">当前接入数量：</span>
            <span class="label label-info">12312</span>
        </div>
        <div class="col-md-2 divCss divBorderHeight" style="padding: 7px 0">
            <span class="col-md-7">累计数据上报数量：</span>
            <span class="label label-info">53434</span>
        </div>
        <div class="col-md-2 divCss divBorderHeight" style="padding: 7px 0">
            <span class="col-md-7">累计车辆VIN上报量：</span>
            <span class="label label-info">23</span>
        </div>
    </div>
    <div class="col-md-12 divCss divBorderHeight" style="height: 815px;">
        <h4>上报日志</h4>
        <hr style="margin-top: 10px; margin-bottom: 10px; border-top: 2px solid #080808;">
        <div class="col-md-12">
            <div id="vinDiv" class="vinDiv" style="border-right: #0673b3 2px solid; width: 12%;">
                <#--<button class="btn btn-primary" onclick="clickVin(VIN123455679238)" type="button" style="padding: 0px;">
                    V123456789123456 <span class="badge">在线</span>
                </button>-->
            </div>
            <div id="vinLogDiv" class="vinDiv" style="width: 88%; padding: 0 15px;">

            </div>
        </div>
    </div>
</div>
</body>
<script>
    var path = "/vehicle/log/";

    var nettyVinTime = null;
    var vinLog = null;
    var vinLogTime = null;

    $(function (){
        nettyVinTime = setInterval("getVin()",1000)
        vinLogTime = setInterval("getLogByVin()",1000)
    })

    function getLogByVin(){
        if (vinLog != null){
            $.muyuSend.get(path+vinLog,undefined,function (response) {
                if (response.code == 100){
                    $("#vinLogDiv").append(response.data);
                }else {
                    $.modal.msgWarning(response.msg);
                }
            })
        }
    }

    /**
     * 切换日志检测对象 并且清空日志
     * */
    function clickVin(vin){
        vinLog = vin;
        $("#vinLogDiv").empty();
    }
    /**
     * 获取上报车辆VIN
     * */
    function getVin(){
        $.muyuSend.get(path+"vin",undefined,function (response) {
            if (response.code == 100){
                $("#vinDiv").empty();
                for (var vin in response.data){
                    $("#vinDiv").append(vinSplicing(vin,response.data[vin]));
                }
            }else {
                $.modal.msgWarning(response.msg);
            }
        })
    }

    /**
     * 拼接VIN展示
     * @param vin
     * @param status
     * @returns {string}
     */
    function vinSplicing(vin , status){
        var vin =
            '<button class="btn btn-primary" onclick="clickVin(\''+vin+'\')" type="button" style="padding: 0px;margin: 5px 0;">'+
                vin+' <span class="badge">'+(status == 1 ? "在线" : "离线")+'</span>'+
            '</button>';
        return vin;
    }

</script>
</html>
