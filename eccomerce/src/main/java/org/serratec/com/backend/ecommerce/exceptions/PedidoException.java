package org.serratec.com.backend.ecommerce.exceptions;

public class PedidoException extends Exception {

	private static final long serialVersionUID = -6808748425879436716L;

	public PedidoException() {
		super();

	}

	public PedidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public PedidoException(String message, Throwable cause) {
		super(message, cause);

	}

	public PedidoException(String message) {
		super(message);

	}

	public PedidoException(Throwable cause) {
		super(cause);

	}
}
