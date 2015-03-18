package com.bidchina.client;



/******************************************
 * 类描述： 分享内容
 * 类名称：ShareMessage  
 * @version: 1.0
 * @author: why
 * @time: 2014-10-9 下午1:23:45 
 ******************************************/
public class ShareMessage {
	private String title;
	private String text;
	private String textDetails;
	private String imageURL;
	private String downURL;
	private BidDetailData detail;
	
	/**
	  * 类的构造方法
	  * 创建一个新的实例 ShareMessage.
	  * @param 
	  * @param text
	  */
	public ShareMessage(String text,String title,String url,String htmlData,BidDetailData detail) {
		this.text = text;
		this.title = title;
		this.textDetails = htmlData;
		this.downURL = url;
		this.detail = detail;
	}
	/**
	 * @return title : return the property title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title : set the property title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return text : return the property text.
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text : set the property text.
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return imageURL : return the property imageURL.
	 */
	public String getImageURL() {
		return imageURL;
	}
	/**
	 * @param imageURL : set the property imageURL.
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	/**
	 * @return downURL : return the property downURL.
	 */
	public String getDownURL() {
		return downURL;
	}
	/**
	 * @param downURL : set the property downURL.
	 */
	public void setDownURL(String downURL) {
		this.downURL = downURL;
	}
	/**
	 * @return the textDetails
	 */
	public String getTextDetails() {
		return textDetails;
	}
	/**
	 * @param textDetails the textDetails to set
	 */
	public void setTextDetails(String textDetails) {
		this.textDetails = textDetails;
	}
	
	public void setDetail(BidDetailData detail) {
		this.detail = detail;
	}
	
	public BidDetailData getDetail() {
		return detail;
	}
}
