package org.serratec.com.backend.ecommerce.exceptions;

public class CategoriaException extends Exception {
	private static final long serialVersionUID = -6001391607647935233L;

	public CategoriaException() {
		super();
	}

	public CategoriaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public CategoriaException(String message, Throwable cause) {
		super(message, cause);

	}

	public CategoriaException(String message) {
		super(message);

	}

	public CategoriaException(Throwable cause) {
		super(cause);

	}

}
