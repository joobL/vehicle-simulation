package com.muyu.netty.client;


import com.muyu.common.Config;
import com.muyu.netty.bean.NettyClientBean;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 牧鱼
 * @Classname NettyClient
 * @Description TODO
 * @Date 2021/7/30
 */
public class NettyClientInit {

    private static final Logger log = LoggerFactory.getLogger(NettyClientInit.class);

    public static void nettyInit(NettyClientBean nettyClientBean){
        NettyClientInit nettyClientInit = new NettyClientInit();
        nettyClientInit.init(nettyClientBean);
    }

    private NettyClientHandler mClientHandler;

    private ChannelFuture future;

    private void init(NettyClientBean nettyClientBean) {
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
            future = b.connect(nettyClientBean.getHost(), nettyClientBean.getPort()).sync();
            if (future.isSuccess()) {
                log.info("Client,链接服务端成功");
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
