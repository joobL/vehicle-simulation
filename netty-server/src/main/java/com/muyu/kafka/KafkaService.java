package com.muyu.kafka;

import com.muyu.common.Config;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 牧鱼
 * @Classname KafkaService
 * @Description TODO
 * @Date 2021/8/9
 */
@Component
public class KafkaService {

    private static final Logger log = LoggerFactory.getLogger(KafkaService.class);

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    public void kafkaSendMsg(String msg){
        RecordMetadata recordMetadata = null;
        try {
            //发送消息
            recordMetadata = kafkaProducer.send(new ProducerRecord<>(Config.TOPIC, msg)).get();
            log.info("recordMetadata: {}，Msg：{}", recordMetadata,msg);
        } catch (Exception e) {
            log.error("send fail, uuid: {}", msg, e);
        }
    }
}
