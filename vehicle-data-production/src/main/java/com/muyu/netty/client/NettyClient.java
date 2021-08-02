package com.muyu.netty.client;


import com.muyu.common.Config;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;

/**
 * @author 牧鱼
 * @Classname NettyServer
 * @Description TODO
 * @Date 2021/7/30
 */
public class NettyClient {

    private String host ;

    private int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    private NettyClientHandler mClientHandler;

    private ChannelFuture future;

    public void init() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            mClientHandler = new NettyClientHandler();
            b.group(workerGroup).channel(NioSocketChannel.class)
                    // KeepAlive
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // Handler
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            //分包器
                            channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(Config.DATA_PACK_SEPARATOR.getBytes())));

                            // 解码器
                            channel.pipeline().addLast("encoder", new StringEncoder());
                            channel.pipeline().addLast("decoder", new StringDecoder());

                            //处理器
                            channel.pipeline().addLast(mClientHandler);
                        }
                    });
            future = b.connect(host, port).sync();
            if (future.isSuccess()) {
                System.out.println("Client,链接服务端成功");
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
