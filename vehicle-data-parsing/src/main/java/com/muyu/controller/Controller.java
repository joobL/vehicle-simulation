package com.muyu.controller;

import com.alibaba.fastjson.JSONObject;
import com.muyu.common.Config;
import com.muyu.common.Response;
import com.muyu.hbase.service.HbaseDataService;
import com.muyu.hbase.service.HbaseTableService;
import com.muyu.parsing.ParsingVehicleDataThread;
import com.muyu.pojo.VehicleData;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private KafkaConsumer<String, String> kafkaConsumer;

    @Autowired
    private HbaseTableService hbaseTableService;

    @Autowired
    private HbaseDataService hbaseDataService;

    @Autowired
    private RedisTemplate<String , ? extends Object> redisTemplate;

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


    @GetMapping(value = "/test")
    public Response test(
            @RequestParam("vin") String vin,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime
                                   ) throws IOException {
        log.info("VIN：{} 请求历史数据 开始时间：{} - 结束时间：{}" , vin , startTime , endTime);
        Filter filter = new RowFilter(CompareFilter.CompareOp.GREATER,new BinaryPrefixComparator((vin + dateToStamp(startTime)).getBytes()));
        Filter filter2 = new RowFilter(CompareFilter.CompareOp.LESS,new BinaryPrefixComparator((vin + dateToStamp(endTime)).getBytes()));
        List<String> dataList = new ArrayList<>();
        ListOperations<String, String> listOperations = (ListOperations<String, String>) redisTemplate.opsForList();

        ResultScanner resultScanner = hbaseDataService.scan("veh_table", filter , filter2);
        for (Result result : resultScanner) {
            Map<String,String> dataMap = new HashMap<>();
            for (Cell cell: result.rawCells()){
                dataMap.put(Bytes.toString(CellUtil.cloneQualifier(cell)),Bytes.toString(CellUtil.cloneValue(cell)));
            };
            dataList.add(
                    JSONObject.toJSONString(
                            ParsingVehicleDataThread.getVehicleData(
                                    JSONObject.parseObject(
                                            JSONObject.toJSONString(dataMap), VehicleData.class))));
        }
        listOperations.leftPushAll(Config.VEHICLE_HISTORY_LOCUS + vin , dataList.toArray(new String[dataList.size()]));
        return Response.success();
    }

    @RequestMapping("/del")
    public void del() throws IOException {
        hbaseTableService.truncate("veh_table");
    }

    public String dateToStamp(String s)  {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
}
