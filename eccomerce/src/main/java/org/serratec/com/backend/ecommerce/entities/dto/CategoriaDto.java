package org.serratec.com.backend.ecommerce.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoriaDto {

	@NotBlank
	@Size(max = 40)
	private String nome;

	@Size(max = 250)
	private String descricao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
