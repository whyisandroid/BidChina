package com.bidchina.client;


/******************************************
 * 类描述： 选项中的一个对象
 * 类名称：VarItem  
 * @version: 1.0
 * @author: ly
 * @time: 2013-12-12 下午12:28:11 
 ******************************************/
public class VarItem {

	private String desc;
	private String code;
	
	public VarItem() {
		super();
	}
	public VarItem(String code, String desc) {
		super();
		this.desc = desc;
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
