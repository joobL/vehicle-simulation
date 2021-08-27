package com.muyu.config;

import com.muyu.hbase.service.HbaseDataService;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author 牧鱼
 * @Classname BeforeClose
 * @Description TODO
 * @Date 2021/8/27
 */
@Component
public class BeforeClose implements DisposableBean {

    private final KafkaConsumer<String, String> kafkaConsumer;

    private final HbaseDataService hbaseDataService;

    public BeforeClose(KafkaConsumer<String, String> kafkaConsumer, HbaseDataService hbaseDataService) {
        this.kafkaConsumer = kafkaConsumer;
        this.hbaseDataService = hbaseDataService;
    }

    @Override
    public void destroy() throws Exception {
        try {
            kafkaConsumer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            hbaseDataService.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
