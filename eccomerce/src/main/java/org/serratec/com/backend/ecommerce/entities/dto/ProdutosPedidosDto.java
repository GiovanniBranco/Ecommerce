package org.serratec.com.backend.ecommerce.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProdutosPedidosDto {

	@NotBlank
	private String nome;

	@NotNull
	private Integer quantidade;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "ProductOrderDto [nome=" + nome + ", quantidade=" + quantidade + "]";
	}

}
