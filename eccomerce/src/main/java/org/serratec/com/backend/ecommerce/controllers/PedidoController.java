package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.dto.CadastroPedidoDto;
import org.serratec.com.backend.ecommerce.entities.dto.PedidoDto;
import org.serratec.com.backend.ecommerce.entities.dto.PedidoFinalizadoDto;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutosPedidosDto;
import org.serratec.com.backend.ecommerce.exceptions.CarrinhoException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.PedidoException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.serratec.com.backend.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	PedidoService pedidoService;

	@GetMapping
	public ResponseEntity<List<PedidoDto>> getAll() {
		return new ResponseEntity<List<PedidoDto>>(pedidoService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{numeroPedido}")
	public ResponseEntity<PedidoDto> getByNumeroPedido(@PathVariable String numeroPedido)
			throws EntityNotFoundException, PedidoException {
		return new ResponseEntity<PedidoDto>(pedidoService.getByNumeroPedido(numeroPedido), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CadastroPedidoDto> create(@RequestBody PedidoDto purchase)
			throws EntityNotFoundException, ProdutoException {
		return new ResponseEntity<CadastroPedidoDto>(pedidoService.order(purchase), HttpStatus.CREATED);
	}

	@PutMapping("/atualizar/{numeroPedido}")
	public ResponseEntity<PedidoDto> adionarProduto(@PathVariable String numeroPedido,
			@RequestBody List<ProdutosPedidosDto> productOrderDto) throws EntityNotFoundException, ProdutoException {
		return new ResponseEntity<PedidoDto>(pedidoService.adicionarProdutoPedido(numeroPedido, productOrderDto),
				HttpStatus.ACCEPTED);
	}

	@PutMapping("/atualizar-quantidade/{numeroPedido}")
	public ResponseEntity<PedidoDto> atualizarQuantidadeProduto(@PathVariable String numeroPedido,
			@RequestBody List<ProdutosPedidosDto> productOrderDto)
			throws EntityNotFoundException, ProdutoException, PedidoException, CarrinhoException {
		return new ResponseEntity<PedidoDto>(
				pedidoService.alterarQuantidadeProdutoPedido(numeroPedido, productOrderDto), HttpStatus.ACCEPTED);
	}

	@PutMapping("/remover-produto/{numeroPedido}")
	public ResponseEntity<PedidoDto> remover(@PathVariable String numeroPedido,
			@RequestBody List<ProdutosPedidosDto> productOrderDto)
			throws EntityNotFoundException, CarrinhoException, ProdutoException, PedidoException {
		pedidoService.deletarProdutoOrder(numeroPedido, productOrderDto);
		return new ResponseEntity<PedidoDto>(pedidoService.deletarProdutoOrder(numeroPedido, productOrderDto),
				HttpStatus.OK);
	}

	@PutMapping("/finalizar-pedido/{numeroPedido}")
	public ResponseEntity<PedidoFinalizadoDto> finalizarPedido(@PathVariable String numeroPedido)
			throws EntityNotFoundException, PedidoException {
		return new ResponseEntity<PedidoFinalizadoDto>(pedidoService.finalizarPedido(numeroPedido),
				HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/cancelar-pedido/{numeroPedido}")
	public ResponseEntity<String> cancelarPedido(@PathVariable String numeroPedido)
			throws EntityNotFoundException, ProdutoException, CarrinhoException, PedidoException {
		return new ResponseEntity<String>(pedidoService.cancelarPedido(numeroPedido), HttpStatus.ACCEPTED);
	}
}