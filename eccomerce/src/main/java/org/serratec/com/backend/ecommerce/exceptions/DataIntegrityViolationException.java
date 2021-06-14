package org.serratec.com.backend.ecommerce.exceptions;

public class DataIntegrityViolationException extends Exception {

	private static final long serialVersionUID = -4562188187241608306L;

	private String msg;

	public DataIntegrityViolationException() {
		super();
	}

	public DataIntegrityViolationException(String message) {
		super(message);
		this.msg = message;
	}

	public String getMsg() {
		return msg;
	}
}
