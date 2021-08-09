package com.muyu.netty.server;

import com.muyu.common.Config;
import com.muyu.config.NettyConfig;
import com.muyu.exception.NettyServerException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 牧鱼
 * @Classname NettyServer
 * @Description TODO
 * @Date 2021/7/30
 */
public class NettyServerInstance {
    private static final Logger log = LoggerFactory.getLogger(NettyServerInstance.class);

    private int nettyPort;

    private NettyServerHandler nettyServerHandler;

    public NettyServerInstance(int nettyPort , NettyServerHandler nettyServerHandler) {
        this.nettyPort = nettyPort;
        this.nettyServerHandler = nettyServerHandler;
    }

    public void init() {
        if (this.nettyPort == 0){
            throw new NettyServerException("请配置netty端口");
        }
        if (this.nettyServerHandler == null){
            throw new NettyServerException("netty业务处理者未进行配置："+NettyServerHandler.class.getName());
        }
        // 接受者
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理者
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                // 指定链接队列大小
                .option(ChannelOption.SO_BACKLOG, 128)
                //KeepAlive
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //Handler
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                    // 分包器
                    channel.pipeline().addLast(new DelimiterBasedFrameDecoder(4096, Unpooled.copiedBuffer(Config.DATA_PACK_SEPARATOR.getBytes())));

                    // 编码器 发送的时候进行编码 -- 默认UTF-8
                    channel.pipeline().addLast("encoder", new StringEncoder());
                    // 解码器 接受的时候进行解码 -- 默认UTF-8
                    channel.pipeline().addLast("decoder", new StringDecoder());

                    // 消息执行类
                    channel.pipeline().addLast(nettyServerHandler);
                    }
                });
            ChannelFuture future = b.bind(nettyPort).sync();
            if (future.isSuccess()) {
                log.info("Server,启动Netty服务端成功，端口号：{}" , nettyPort);
            }
            Channel channel = future.channel();
            NettyConfig.setChannel(channel);
            // 程序会一直堵塞到主动执行关闭
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             workerGroup.shutdownGracefully();
             bossGroup.shutdownGracefully();
        }
    }
}
