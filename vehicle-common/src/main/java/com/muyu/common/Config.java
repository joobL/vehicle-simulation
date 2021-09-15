package com.muyu.common;

import io.netty.channel.ChannelHandlerContext;

/**
 * netty 核心配置
 */
public class Config {
	/**
	 * 分包符
	 */
	public static final String DATA_PACK_SEPARATOR = "#$&*";
	public static ChannelHandlerContext ctx;

	/**
	 * TopIc
	 */

	public static final String TOPIC = "vehicle-data";

	/**
	 * kafka服务器地址
	 */
	public final static String BOOTSTRAP_SERVERS = "139.196.121.76:9092";

	/**
	 * 车辆在线
	 */
	public static final Integer VEHICLE_STATUS_ONLINE = 1;

	/**
	 * 车辆离线
	 */
	public static final Integer VEHICLE_STATUS_OFFLINE = 0;

	/**
	 * 车辆消息报文前缀
	 */
	public final static String VEHICLE_MSG_SUF = "vehicle_msg:";
	/**
	 * 车辆启动报文前缀
	 */
	public final static String VEHICLE_START_SUF = "vehicle_start:";
	/**
	 * 车辆关闭报文前缀
	 */
	public final static String VEHICLE_STOP_SUF = "vehicle_stop:";

	/**
	 * 车辆上线通道
	 */
	public final static String VEHICLE_START_LINE = "vehicle_start_aisle";

	/**
	 * 车辆下线通道
	 */
	public final static String VEHICLE_STOP_LINE = "vehicle_stop_aisle";

	/**
	 * 连接端口报文
	 */
	public final static String CLOSE = "close";

	/**
	 * 车辆VIN正则表达式
	 */
	public final static String VIN_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{17}$";
}
