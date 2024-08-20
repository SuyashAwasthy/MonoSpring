package com.techlabs.app.exception;

public class NoContactFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NoContactFoundException(String message) {
		super(message);
	}

}
