package com.bidchina.client.net;
public class BusinessException extends CommonException{
	private static final long serialVersionUID=-8190213457556819L;
	
	public BusinessException(ErrorMessage em){
		super(em);
	}
	
	public BusinessException(){
		
	}
}
