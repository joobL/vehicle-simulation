package com.muyu.netty.client;

import com.muyu.common.Config;
import com.muyu.netty.log.NettyLogQueue;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(NettyClientHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("客户端与服务器建立连接成功");
		Config.ctx = ctx;
		NettyLogQueue.add("<p>成功与服务器建立连接</p>");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("接收到服务端发来的消息:" + msg);
		NettyLogQueue.add("<p>接收到服务端发来的消息"+msg+"</p>");
		if ("断开".equals(msg)){
			ctx.writeAndFlush("客户端退出").addListener(ChannelFutureListener.CLOSE);
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.info("客户端捕获异常：{}",cause.getMessage());
		cause.printStackTrace();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("隧道切换不活跃状态");
	}


}
