package com.muyu.hbase.service;

import com.muyu.hbase.instance.HbaseInstance;
import com.muyu.hbase.service.base.HbaseService;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceExistException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 牧鱼
 * @Classname HbaseTable
 * @Description TODO
 * @Date 2021/8/26
 */
@Component
public class HbaseTableService extends HbaseService {

    private Connection conn = null;
    private Admin admin = null;

    public HbaseTableService(HbaseInstance hbaseInstance) {

        conn = hbaseInstance.getConn();
        admin = hbaseInstance.getAdmin();
    }

    /**
     * 判断表是否存在
     * @param tablesName
     * @return
     */
    public boolean isTableExist(String tablesName){
        boolean exists = false;
        try {
            exists = admin.tableExists(TableName.valueOf(tablesName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }


    /**
     * 创建数据表
     * @param tableName
     * @param columnFamilies
     * @throws IOException
     */
    public void createTable(String tableName,String ...columnFamilies) throws IOException {
        TableName name = TableName.valueOf(tableName);
        if (!admin.isTableAvailable(name)) {
            //表描述器构造器
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(name);
            List<ColumnFamilyDescriptor> columnFamilyDescriptorList = new ArrayList<>();
            for (String columnFamily : columnFamilies) {
                //列族描述起构造器
                ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(columnFamily));
                //获得列描述
                ColumnFamilyDescriptor columnFamilyDescriptor = columnFamilyDescriptorBuilder.build();
                columnFamilyDescriptorList.add(columnFamilyDescriptor);
            }
            // 设置列簇
            tableDescriptorBuilder.setColumnFamilies(columnFamilyDescriptorList);
            //获得表描述器
            TableDescriptor tableDescriptor = tableDescriptorBuilder.build();
            //创建表
            admin.createTable(tableDescriptor);
        }
    }

    /**
     * 禁用表
     * @param tableName
     * @throws IOException
     */
    public void disableTable(String tableName) throws IOException {
        TableName name = TableName.valueOf(tableName);
        if (!admin.isTableDisabled(name)) {
            admin.disableTable(name);
        }
    }

    /**
     * 清空表
     * @param tableName
     */
    public void truncate(String tableName) throws IOException {

        TableName name = TableName.valueOf(tableName);
        disableTable(tableName);
        admin.truncateTable(name,true);

    }

    /**
     * 删除表
     * @param tableName
     * @throws IOException
     */
    public void deleteTable(String tableName) throws IOException {
        TableName name = TableName.valueOf(tableName);
        if (admin.isTableDisabled(name)) {
            admin.deleteTable(name);
        }
    }

    /**
     * 创建命名空间
     * @param nameSpace
     */
    public void createTableSpace(String nameSpace){
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(nameSpace).build();

        try {
            admin.createNamespace(namespaceDescriptor);
        }catch (NamespaceExistException e){
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取表的列簇
     * @param tableName
     * @return
     * @throws IOException
     */
    public List<String> getColumnFamiliesName(String tableName)throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        List<String> list=new ArrayList<>();
        TableDescriptor descriptor = table.getDescriptor();
        ColumnFamilyDescriptor[] columnFamilies = descriptor.getColumnFamilies();
        for(ColumnFamilyDescriptor columnFamilyDescriptor : columnFamilies){
            list.add(columnFamilyDescriptor.getNameAsString());
        }
        return list;
    }
    /**
     * 获取数据表列表
     * @return
     * @throws IOException
     */
    public List<TableDescriptor> listTables() throws IOException {
        List<TableDescriptor> tableDescriptors = admin.listTableDescriptors();
        return tableDescriptors;
    }
}
