package com.bidchina.client;

import java.util.List;

public class BidData {
	private String total_page;
	private String current_page;
	private List<Bid> data;
	/**
	 * @return the total_page
	 */
	public String getTotal_page() {
		return total_page;
	}
	/**
	 * @param total_page the total_page to set
	 */
	public void setTotal_page(String total_page) {
		this.total_page = total_page;
	}
	/**
	 * @return the current_page
	 */
	public String getCurrent_page() {
		return current_page;
	}
	/**
	 * @param current_page the current_page to set
	 */
	public void setCurrent_page(String current_page) {
		this.current_page = current_page;
	}
	/**
	 * @return the data
	 */
	public List<Bid> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<Bid> data) {
		this.data = data;
	}
	
	
}
