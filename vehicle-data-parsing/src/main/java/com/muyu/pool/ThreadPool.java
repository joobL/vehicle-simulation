package com.muyu.pool;

import java.util.concurrent.*;

/**
 * @author 牧鱼
 * @Classname ThreadPool
 * @Description TODO
 * @Date 2021/8/26
 */
public class ThreadPool {
    private static ScheduledExecutorService scheduledThreadPool = null;
    static {
        /**
         * 定长线程池，支持定时及周期性任务执行——定期执行
         */
        scheduledThreadPool = Executors.newScheduledThreadPool(10);
    }

    /**
     * 以固定时间安排 执行线程
     * @param command 线程任务
     * @param initialDelay 延迟秒数
     * @param period 执行间隔
     * @param unit 时间格式
     */
    public static void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit){
        //initialDelay：延迟秒数，period：执行间隔  unit：时间格式
        scheduledThreadPool.scheduleAtFixedRate(command, initialDelay, period, unit);
    }


}
