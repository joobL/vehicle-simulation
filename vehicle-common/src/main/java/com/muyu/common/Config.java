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
	 * 车辆是否需要报文通道
	 */
	public final static String VEHICLE_IS_MSG = "vehicle_msg_aisle";

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


	/**
	 * 车辆基础故障组
	 */
	public final static String VEHICLE_BASE_FAULT = "vehicle_base";

	/**
	 * 车辆零配件故障组
	 */
	public final static String VEHICLE_PARTS_FAULT = "vehicle_parts";

	/**
	 * 车辆电池故障组
	 */
	public final static String VEHICLE_BATTERY_FAULT = "vehicle_battery";

	/**
	 * 车辆故障日志队列
	 */
	public final static String VEHICLE_FAULT_LOG = "vehicle_fault_log";

	/**
	 * 车辆绑定电子围栏
	 */
	public final static String VEHICLE_FENCE_BIND = "vehicle_fence_bind";
	/**
	 * 车辆电子围栏信息
	 */
	public final static String VEHICLE_FENCE_INFO = "vehicle_fence_INFO";

	/**
	 * 车辆电子围栏类型
	 */
	public final static String VEHICLE_FENCE_TYPE = "vehicle_fence_type";

	/**
	 * 车辆历史轨迹查询
	 */
	public final static String VEHICLE_HISTORY_LOCUS = "vehicle_history_locus:";
}
