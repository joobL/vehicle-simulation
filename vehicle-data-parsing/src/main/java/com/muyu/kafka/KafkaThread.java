package com.muyu.kafka;

import com.alibaba.fastjson.JSONObject;
import com.muyu.common.Config;
import com.muyu.hbase.service.HbaseDataService;
import com.muyu.parsing.ParsingVehicleDataThread;
import com.muyu.pojo.VehicleData;
import com.muyu.pool.ThreadPool;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.time.Duration;

/**
 * @author 牧鱼
 * @Classname KafkaThread
 * @Description TODO
 * @Date 2021/8/26
 */
public class KafkaThread implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(KafkaThread.class);

    private final KafkaConsumer<String, String> kafkaConsumer;
    private final HbaseDataService hbaseDataService;
    private final RedisTemplate<String , ? extends Object> redisTemplate;

    public KafkaThread(KafkaConsumer<String, String> kafkaConsumer, HbaseDataService hbaseDataService, RedisTemplate<String, ?> redisTemplate) {
        this.kafkaConsumer = kafkaConsumer;
        this.hbaseDataService = hbaseDataService;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void run() {
        log.info("开始消费kafka消息");
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1));
        for (ConsumerRecord<String, String> record : records.records(Config.TOPIC)) {
            String message = record.value();
            VehicleData vehicleData = JSONObject.parseObject(message, VehicleData.class);
            try {
                String rowKey = vehicleData.getVin()+System.currentTimeMillis();
                hbaseDataService.insertAll("veh_table",
                        rowKey,
                        "base", vehicleData.getBaseData());
                log.info("接收到车辆消息: {},已经成功入库，rowKey：{}", vehicleData.getVin() , rowKey);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                /** 无论hbase是否可以成功，都需要去判断是否需要分流 */
                ThreadPool.exeThread(new ParsingVehicleDataThread(redisTemplate,vehicleData));
            }
        }
        log.info("结束消费kafka消息");
    }

}
