package com.muyu;

import com.muyu.hbase.service.base.HbaseService;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Demo {
    private static final Logger log = LoggerFactory.getLogger(Demo.class);
    static String tableName = "veh_table";
    static String[] columnFamilies = new String[]{"base","msg"};
    static String VIN = "VIN1000";
    public static void main() throws IOException, InterruptedException {
        // 创建表
//        HbaseUtil.createTable(tableName,columnFamilies);

        // 查询表的列簇
//        List<String> rowName = HbaseService.getColumnFamiliesName(tableName);
//        System.out.println(rowName);
//        testData();
//        Result base = HbaseUtil.select(tableName, "VIN10004979");
//        for (Cell cell: base.rawCells()){
//            System.out.println("cloneRow:"+Bytes.toString(CellUtil.cloneRow(cell)));;
//            System.out.println("cloneFamily:"+Bytes.toString(CellUtil.cloneFamily(cell)));;
//            System.out.println("cloneValue:"+Bytes.toString(CellUtil.cloneValue(cell)));;
//            System.out.println("cloneQualifier:"+Bytes.toString(CellUtil.cloneQualifier(cell)));;
//        };
        /*ResultScanner resultScanner = HbaseService.scan(tableName);
        for (Result result : resultScanner) {
            for (Cell cell: result.rawCells()){
                System.out.println("cloneRow:"+Bytes.toString(CellUtil.cloneRow(cell)));;
                System.out.println("cloneFamily:"+Bytes.toString(CellUtil.cloneFamily(cell)));;
                System.out.println("cloneValue:"+Bytes.toString(CellUtil.cloneValue(cell)));;
                System.out.println("cloneQualifier:"+Bytes.toString(CellUtil.cloneQualifier(cell)));;
            };
        }*/

//        System.out.println(base);

        //模拟数据
//      testData();
        // hash    hash    hash    hash
        // note -> note -> note -> note
        // [↑]      []      []      []
        // note
        //  ↑
        // note


//        String admin = HbaseUtil.getAllRecord(tableName);
//        System.out.println(admin);


//        HbaseUtil.deleteTable("admin");
//        List<TableDescriptor> tableDescriptors = HbaseUtil.listTables();
//        System.out.println(tableDescriptors);
//        List<String> admin = HbaseUtil.getRowName("admin");
//        System.out.println(admin);
//        long l = System.currentTimeMillis();
//        HbaseUtil.insertOne("admin",String.valueOf(l),"age","age1","你好，java");
////        HbaseUtil.insertOne("admin",String.valueOf(System.currentTimeMillis()),"age","age2","20");
////        HbaseUtil.insertOne("admin",String.valueOf(System.currentTimeMillis()),"age","age3","20");
////
//        Result select = HbaseUtil.select("admin", String.valueOf(l));
//        System.out.println(l);
//        System.out.println(select);
//        System.out.println(new String(select.getValue("age".getBytes(),"age1".getBytes())));

//        HbaseService.close();
    }

    public static void testData() throws IOException, InterruptedException {
        /*for (int i = 0; i < 15; i++) {
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("speed",String.valueOf(100+i));
            dataMap.put("longitude","10."+i);
            dataMap.put("latitude","10");
            dataMap.put("voltage",String.valueOf(i));

            String rowKey = VIN + String.valueOf(System.currentTimeMillis()).substring(5);
            HbaseService.insertAll(tableName,rowKey,"base",dataMap);

            log.info("添加数据rowKey：{} ，数据集：{}",rowKey,dataMap);
            Thread.sleep(1000);
        }*/
    }

}
