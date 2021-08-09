package com.muyu.controller;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    @RequestMapping("/kafkaClientsSend")
    public String send() {
        String uuid = UUID.randomUUID().toString();
        RecordMetadata recordMetadata = null;
        try {
        	//将消息发送到Kafka服务器的名称为“one-more-topic”的Topic中
            recordMetadata = kafkaProducer.send(new ProducerRecord<>("one-more-topic", uuid)).get();
            log.info("recordMetadata: {}", recordMetadata);
            log.info("uuid: {}", uuid);
        } catch (Exception e) {
            log.error("send fail, uuid: {}", uuid, e);
        }
        return uuid;
    }
}
