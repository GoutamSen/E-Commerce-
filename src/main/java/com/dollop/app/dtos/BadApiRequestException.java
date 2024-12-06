package com.dollop.app.dtos;

public class BadApiRequestException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadApiRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public BadApiRequestException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}	
}
