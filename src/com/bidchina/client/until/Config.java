package com.bidchina.client.until;



/**
 * **************************************** 
 * 类描述： 配置信息类 类名称：Config
 * @version: 1.0
 * @author: why
 * @time: 2014-2-14 下午3:32:29
 ***************************************** 
 */
public class Config {

	/** 是否调试. */
	public final static boolean DEBUG = true;
	
	// 测试
	public final static String MY_SERVICE = "http://zhaobiaogg.linuxsong.org";
	/** 临时文件保存路径. */
	
	//获取招标信息
	public final static String HTTP_SEARCH = MY_SERVICE+ "/api/search";
	//获取招标信息详情
	public final static String HTTP_SEARCH_detail = MY_SERVICE+ "/api/detail";
}
