package com.muyu.hbase.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Hbase-Conf配置
 *
 * @Author: yuanj
 * @Date: 2018/10/12 10:49
 */
@Configuration
@ConfigurationProperties(prefix = HbaseConfig.CONF_PREFIX)
public class HbaseConfig {


    // 行键前缀固定长度，不足的用0补齐（后补）
    public static final int ROW_KEY_PRE_LENGTH = 40;
    // 行键参数固定长度，不足用0补齐（前补）
    public static final int ROW_KEY_OPTIONS_LENGTH = 100;
    // 行键前缀固定长度，不足的用0补齐（前补）
    public static final int ROW_KEY_SUF_LENGTH = 40;


    public static final String CONF_PREFIX = "hbase.conf";

    private Map<String,String> confMaps;

    public Map<String, String> getConfMaps() {
        return confMaps;
    }
    public void setConfMaps(Map<String, String> confMaps) {
        this.confMaps = confMaps;
    }
}
