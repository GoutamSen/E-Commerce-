package com.dollop.app.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {             //alt+shift+S+c
		super();
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

    
}
