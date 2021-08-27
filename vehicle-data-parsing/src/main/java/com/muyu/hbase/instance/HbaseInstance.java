package com.muyu.hbase.instance;

import com.muyu.hbase.config.HbaseConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author 牧鱼
 * @Classname HbaseInstance
 * @Description TODO
 * @Date 2021/8/26
 */
@Component
public class HbaseInstance {

    private Configuration conf;
    private Connection conn = null;
    private Admin admin = null;

    public HbaseInstance(HbaseConfig hbaseConfig) throws IOException {
        conf = HBaseConfiguration.create();
        Map<String, String> confMaps = hbaseConfig.getConfMaps();
        confMaps.forEach((key,val) -> {
            conf.set(key,val);
        });
        conn = ConnectionFactory.createConnection(conf);
        admin = conn.getAdmin();
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
