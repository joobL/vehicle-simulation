<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Netty监控台</title>
</head>
<link rel="stylesheet" href="/static/bootstrap-3.4.1/css/bootstrap.css"/>
<script src="/static/vehicle/js/jquery.min.js"></script>
<script src="/static/bootstrap-3.4.1/js/bootstrap.js"></script>
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
    .vinDiv span{
        float: left;
        margin: 5px 15px 5px 0;
        font-size: 15px;
        cursor:pointer;
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
            <div class="vinDiv" style="border-right: #0673b3 2px solid; width: 10%;">
                <span class="label label-info" >V123456789123456</span>
            </div>
            <div class="vinDiv" style="width: 90%; padding: 0 15px;">
                <p>Server,channelActive</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"DIWF8698DIIW87"</p>
                <p>}</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"DIWF8698DIIW87"</p>
                <p>}</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"1232341412"</p>
                <p>}</p>
                <p>Server,channelActive</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"DIWF8698DIIW87"</p>
                <p>}</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"DIWF8698DIIW87"</p>
                <p>}</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"1232341412"</p>
                <p>}</p>
                <p>Server,channelActive</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"DIWF8698DIIW87"</p>
                <p>}</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"DIWF8698DIIW87"</p>
                <p>}</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"1232341412"</p>
                <p>}</p>
                <p>Server,channelActive</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"DIWF8698DIIW87"</p>
                <p>}</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"DIWF8698DIIW87"</p>
                <p>}</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"1232341412"</p>
                <p>}</p>
                <p>Server,channelActive</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"DIWF8698DIIW87"</p>
                <p>}</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"DIWF8698DIIW87"</p>
                <p>}</p>
                <p>Server,接收到客户端发来的消息:{</p>
                <p>"VIN":"1232341412"</p>
                <p>}</p>
            </div>
        </div>
    </div>
</div>
</body>
<script>

</script>
</html>
