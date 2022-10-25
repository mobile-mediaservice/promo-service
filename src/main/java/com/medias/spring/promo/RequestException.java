package com.medias.spring.promo;

public class RequestException extends Exception {
	String details;
	RequestException(String message){
		super();
		this.details = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
}
