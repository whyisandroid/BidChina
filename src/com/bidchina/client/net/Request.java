package com.bidchina.client.net;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/******************************************
	 * 类描述： 请求数据封装�?
	 * 类名称：Request  
 	 * @version: 1.0
	 * @author: why
	 * @time: 2013-10-21 下午3:01:21 
*****************************************/
public class Request<T> implements java.io.Serializable {
	
	public static final int LOGIN_REQUEST = 1;

	/**Afinal  数据 key**/
	public static final String  AJAXPARAMS = "AjaxParams";
	public static final String  PICTURE = "pictureParams";

	private static final long serialVersionUID = 2013415890997784131L;

	private int type;

	private UUID sessionId;
	
	/**访问地址**/
	private String url;
	
	/**返回类型**/
	public Class<T> r_calzz;
	/**请求数据**/
	private Map<String, Object> parameters;

	public UUID getSessionId() {
		return sessionId;
	}
	
	
	/**
	 * @return r_calzz : return the property r_calzz.
	 */
	public Class<T> getR_calzz() {
		return r_calzz;
	}
	
	/**
	 * @param r_calzz : set the property r_calzz.
	 */
	public void setR_calzz(Class<T> r_calzz) {
		this.r_calzz = r_calzz;
	}
	/**
	 * @return url : return the property url.
	 */
	public String getUrl() {
		return url;
	}



	/**
	 * @param url : set the property url.
	 */
	public void setUrl(String url) {
		this.url = url;
	}



	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}



	public Request(int type) {
		this();
		this.type = type;
	}

	public Request() {
		parameters = new HashMap<String, Object>();
	}

	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}

	public Object getParameter(String name) {
		return parameters.get(name);
	}

	public Map<String, Object> getparameters() {
		return parameters;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
