package com.muyu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author 牧鱼
 * @Classname ColseBefore
 * @Description TODO
 * @Date 2021/8/1
 */
@Component
public class CloseBefore implements DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(CloseBefore.class);

    @Override
    public void destroy() throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("服务器资源销毁开始");
        destroyNettyServer();
        log.info("服务器资源销毁结束耗时：{}",(System.currentTimeMillis() - startTime));
    }

    /**
     * 服务器关闭时销毁netty服务器
     */
    public void destroyNettyServer(){
        long startTime = System.currentTimeMillis();
        log.info("netty毁结开始：{}",startTime);
        NettyConfig.getChannel().closeFuture();
        log.info("netty毁结结束耗时：{}",(System.currentTimeMillis() - startTime));
    }
}
