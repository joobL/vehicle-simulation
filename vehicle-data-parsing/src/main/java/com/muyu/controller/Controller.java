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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private KafkaConsumer<String, String> kafkaConsumer;

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
}
