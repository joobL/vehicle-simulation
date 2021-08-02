package com.muyu.netty.client;

import com.muyu.common.Config;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.Charset;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client,channelActive");
		Config.ctx = ctx;

		new Thread(() ->{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 1; i <= 20; i++) {
				if (Config.ctx != null){
					Config.ctx.writeAndFlush("测试数据——"+i + Config.DATA_PACK_SEPARATOR);
					System.out.println("发送模拟数据：测试数据——"+i);
				}
//                Config.infoQueue.offer("测试数据——"+i);
//                System.out.println("生成模拟数据：测试数据——"+i);
			}
			Config.ctx.writeAndFlush("close"+ Config.DATA_PACK_SEPARATOR);
			System.out.println("生成模拟数据：close");
		}).start();

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Client,接收到服务端发来的消息:" + msg);
		/*var ref = new Object() {
			Boolean isContinue = true;
		};
		new Thread(() -> {

			while (ref.isContinue){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String poll = Config.infoQueue.poll();
				System.out.println("执行检测："+(poll == null ? "无消息":poll));
				if (poll != null){
					ctx.writeAndFlush(poll);
				}
			}
			System.out.println("扫描线程已终止》》》》》》》》》》》》");
		}).start();*/
		if ("断开".equals(msg)){
			ctx.writeAndFlush("系统退出").addListener(ChannelFutureListener.CLOSE);
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("Client,exceptionCaught");
		cause.printStackTrace();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client,channelInactive");
	}


}
