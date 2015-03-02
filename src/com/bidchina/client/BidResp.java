package com.bidchina.client;

public class BidResp {
	private String code;
	private String msg;
	private BidData data;
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the data
	 */
	public BidData getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(BidData data) {
		this.data = data;
	} 
	
	
}
