package com.muyu.config;

import com.muyu.netty.server.NettyServerInstance;
import com.muyu.netty.server.NettyServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author 牧鱼
 * @Classname RunAfter
 * @Description TODO
 * @Date 2021/8/1
 */
@Component
public class RunAfter implements ApplicationRunner {

    private Logger log = LoggerFactory.getLogger(RunAfter.class);

    private int nettyPort;
    private NettyServerHandler nettyServerHandler;

    public RunAfter(@Value("${netty.port}") int port , NettyServerHandler nettyServerHandler) {
        this.nettyPort = port;
        this.nettyServerHandler = nettyServerHandler;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("服务器资源初始化开始");
        new Thread(() -> {
            initNetty();
        },"netty工作线程").start();
        log.info("服务器资源初始化结束耗时：{}",(System.currentTimeMillis() - startTime));
    }

    /**
     * 程序启动后 对netty服务器进行初始化
     */
    private void initNetty() {
        log.info("netty初始化开始");
        NettyServerInstance nettyServerInstance = new NettyServerInstance(this.nettyPort, this.nettyServerHandler);
        nettyServerInstance.init();
    }

}
