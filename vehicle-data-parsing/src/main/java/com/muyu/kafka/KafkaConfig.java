package com.muyu.kafka;

import com.muyu.common.Config;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Properties;

@Configuration
public class KafkaConfig {

    public final static String groupId = "kafka-clients-group";

    @Bean(destroyMethod = "close")
    public KafkaConsumer<String, String> kafkaConsumer() {
        Properties props = new Properties();
        //设置Kafka服务器地址
        props.put("bootstrap.servers", Config.BOOTSTRAP_SERVERS);
        //设置消费组
        props.put("group.id", groupId);
        //设置数据key的反序列化处理类
        props.put("key.deserializer", StringDeserializer.class.getName());
        //设置数据value的反序列化处理类
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", "10");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
        //订阅名称为“one-more-topic”的Topic的消息
        kafkaConsumer.subscribe(Arrays.asList(Config.TOPIC));
        return kafkaConsumer;
    }
}
