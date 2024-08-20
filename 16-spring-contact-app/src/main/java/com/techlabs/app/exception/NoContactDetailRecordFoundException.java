package com.techlabs.app.exception;

public class NoContactDetailRecordFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NoContactDetailRecordFoundException(String message) {
		super(message);
	}
}
