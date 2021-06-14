package org.serratec.com.backend.ecommerce.entities.dto;

import javax.validation.constraints.NotNull;

public class CarrinhoDto {

	private Double preco;

	@NotNull
	private Integer quantidade;

	@NotNull
	private Long produto;

	private Long pedido;

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Long getProduto() {
		return produto;
	}

	public void setProduto(Long produto) {
		this.produto = produto;
	}

	public Long getPedido() {
		return pedido;
	}

	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}
}
