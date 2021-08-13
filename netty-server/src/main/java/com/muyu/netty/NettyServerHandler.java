package com.muyu.netty;

import com.muyu.common.Config;
import com.muyu.kafka.KafkaService;
import com.muyu.log.VehicleLog;
import com.muyu.utils.SpringUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author muyu
 *
 * netty 消息处理者
 */
@ChannelHandler.Sharable
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

	private KafkaService kafkaService;
	public NettyServerHandler() {
		kafkaService = SpringUtils.getBean(KafkaService.class);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("接到客户端连接》》》》》");
		ctx.writeAndFlush("连接已建立" + Config.DATA_PACK_SEPARATOR);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String strMsg = String.valueOf(msg);
		log.info("Server,接收到客户端发来的消息:" + strMsg);
		// 是否是断开消息报文
		if (Config.CLOSE.equals(strMsg)){
			log.info("与服务器断开连接");
			ctx.writeAndFlush("断开"+ Config.DATA_PACK_SEPARATOR);
		}else {
			// 是否是车辆上报数据
			if (strMsg.indexOf(Config.VEHICLE_MSG_SUF) > -1){
				strMsg = strMsg.replace(Config.VEHICLE_MSG_SUF , "");
				String vin = strMsg.substring(0, 17);
				// 判断是否是VIN
				if (vin.matches(Config.VIN_REGEX)){
					strMsg = strMsg.substring(17);
					// 向kafka中发送消息
					kafkaService.kafkaSendMsg(strMsg);
					VehicleLog.vinAddLog(vin,strMsg);
				}
			}
			// 车辆启动报文
			else if (strMsg.indexOf(Config.VEHICLE_START_SUF) > -1){
				strMsg = strMsg.replace(Config.VEHICLE_START_SUF , "");
				VehicleLog.vinOnLine(strMsg);
			}
			// 车辆关闭报文
			else if (strMsg.indexOf(Config.VEHICLE_STOP_SUF) > -1){
				strMsg = strMsg.replace(Config.VEHICLE_STOP_SUF , "");
				VehicleLog.vinOffLine(strMsg);
			}

		}
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.info("Server,exceptionCaught");
		cause.printStackTrace();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("Server,channelInactive");
	}



}
