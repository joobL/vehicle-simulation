package com.muyu.config;

import com.muyu.common.Config;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author muyu
 */
@Configuration
public class KafkaConfig {

    @Bean(destroyMethod = "close")
    public KafkaProducer<String, String> kafkaProducer() {
        Properties props = new Properties();
        //设置Kafka服务器地址
        props.put("bootstrap.servers", Config.BOOTSTRAP_SERVERS);
        //设置数据key的序列化处理类
        props.put("key.serializer", StringSerializer.class.getName());
        //设置数据value的序列化处理类
        props.put("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        return producer;
    }
}
