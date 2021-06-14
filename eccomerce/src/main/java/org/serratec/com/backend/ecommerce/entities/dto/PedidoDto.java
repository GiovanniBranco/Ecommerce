package org.serratec.com.backend.ecommerce.entities.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.serratec.com.backend.ecommerce.entities.CarrinhoEntity;
import org.serratec.com.backend.ecommerce.enums.StatusCompra;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PedidoDto {

	private String numeroPedido;

	private Double valorTotal;

	private StatusCompra status;

	@NotNull
	private Long cliente;

	@JsonIgnore
	private List<CarrinhoEntity> carrinhos;

	private List<ProdutosPedidosDto> produto;

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public StatusCompra getStatus() {
		return status;
	}

	public void setStatus(StatusCompra status) {
		this.status = status;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public List<CarrinhoEntity> getCarrinhos() {
		return carrinhos;
	}

	public void setCarrinhos(List<CarrinhoEntity> carrinhos) {
		this.carrinhos = carrinhos;
	}

	public List<ProdutosPedidosDto> getProduto() {
		return produto;
	}

	public void setProduto(List<ProdutosPedidosDto> produto) {
		this.produto = produto;
	}

}
