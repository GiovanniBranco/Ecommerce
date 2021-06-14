package org.serratec.com.backend.ecommerce.exceptions;

public class CarrinhoException extends Exception {

	private static final long serialVersionUID = -8423311872073902048L;

	public CarrinhoException() {
		super();

	}

	public CarrinhoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public CarrinhoException(String message, Throwable cause) {
		super(message, cause);

	}

	public CarrinhoException(String message) {
		super(message);

	}

	public CarrinhoException(Throwable cause) {
		super(cause);

	}
}
