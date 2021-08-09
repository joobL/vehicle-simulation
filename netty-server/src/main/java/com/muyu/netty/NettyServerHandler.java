package com.muyu.netty;

import com.muyu.common.Config;
import com.muyu.kafka.KafkaService;
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
		log.info("Server,channelActive");
		ctx.writeAndFlush("你好客户端" + Config.DATA_PACK_SEPARATOR);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("Server,接收到客户端发来的消息:" + msg);
		if ("close".equals(msg)){
			log.info("与服务器断开连接");
			ctx.writeAndFlush("断开"+ Config.DATA_PACK_SEPARATOR);
		}else {
			kafkaService.kafkaSendMsg(String.valueOf(msg));
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
