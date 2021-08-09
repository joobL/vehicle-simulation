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
}
