package org.serratec.com.backend.ecommerce.exceptions;

public class ProdutoException extends Exception {

	private static final long serialVersionUID = 3443302053542415025L;

	public ProdutoException() {
		super();

	}

	public ProdutoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public ProdutoException(String message, Throwable cause) {
		super(message, cause);

	}

	public ProdutoException(String message) {
		super(message);

	}

	public ProdutoException(Throwable cause) {
		super(cause);

	}
}
