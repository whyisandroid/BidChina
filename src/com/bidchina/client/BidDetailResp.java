package com.bidchina.client;

import java.io.Serializable;

public class BidDetailResp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6941913410153212481L;
	private String code;
	private String msg;
	private BidDetailData data;
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
	public BidDetailData getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(BidDetailData data) {
		this.data = data;
	}
	
	
	
}
