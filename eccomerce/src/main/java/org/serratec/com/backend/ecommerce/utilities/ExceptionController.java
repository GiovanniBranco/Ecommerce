package org.serratec.com.backend.ecommerce.utilities;

import org.serratec.com.backend.ecommerce.exceptions.CarrinhoException;
import org.serratec.com.backend.ecommerce.exceptions.CategoriaException;
import org.serratec.com.backend.ecommerce.exceptions.ClienteException;
import org.serratec.com.backend.ecommerce.exceptions.EnderecoRepetidoException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.PedidoException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	private HttpHeaders header(Exception ex) {
		HttpHeaders header = new HttpHeaders();
		header.add("LIBRARY", "ECOMMERCE_V1");
		header.add("x-error-msg", ex.getMessage());

		return header;
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handlerEntityNotFoundException(EntityNotFoundException ex) {
		return ResponseEntity.notFound().headers(this.header(ex)).build();
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
		return ResponseEntity.badRequest().headers(this.header(e)).build();
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handlerNullPointerException(NullPointerException e) {
		return ResponseEntity.badRequest().headers(this.header(e)).build();
	}

	@ExceptionHandler(CategoriaException.class)
	public ResponseEntity<String> handlerCategoryException(CategoriaException e) {
		return ResponseEntity.badRequest().headers(this.header(e)).build();
	}

	@ExceptionHandler(ProdutoException.class)
	public ResponseEntity<String> handlerProductException(ProdutoException e) {
		return ResponseEntity.badRequest().headers(this.header(e)).build();
	}

	@ExceptionHandler(ClienteException.class)
	public ResponseEntity<String> handlerClienteException(ClienteException e) {
		return ResponseEntity.badRequest().headers(this.header(e)).build();
	}

	@ExceptionHandler(PedidoException.class)
	public ResponseEntity<String> handlerPedidoException(PedidoException e) {
		return ResponseEntity.badRequest().headers(this.header(e)).build();
	}

	@ExceptionHandler(CarrinhoException.class)
	public ResponseEntity<String> handlerCarrinhoException(CarrinhoException e) {
		return ResponseEntity.badRequest().headers(this.header(e)).build();
	}

	@ExceptionHandler(EnderecoRepetidoException.class)
	public ResponseEntity<String> handlerEnderecoRepetidoException(EnderecoRepetidoException e) {
		return ResponseEntity.badRequest().headers(this.header(e)).build();
	}
}
