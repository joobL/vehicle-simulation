package com.muyu.controller;

import com.muyu.common.Config;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private KafkaConsumer<String, String> kafkaConsumer;

    @Autowired
//    private HBaseUtils hBaseUtils;

    @RequestMapping("/receive")
    public List<String> receive() {
    	//从Kafka服务器中的名称为“one-more-topic”的Topic中消费消息
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1));
        List<String> messages = new ArrayList<>(records.count());
        for (ConsumerRecord<String, String> record : records.records(Config.TOPIC)) {
            String message = record.value();
            log.info("message: {}", message);
            messages.add(message);
        }
        return messages;
    }


    @RequestMapping(value = "/test")
    public Map<String,Object> test() {
        String str = null;
        /*try {
            //扫描表
//            str = hBaseUtils.scanAllRecord("sixmonth");
//            System.out.println("获取到hbase的内容："+str);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("hbaseContent",str);
        return map;
    }
}
