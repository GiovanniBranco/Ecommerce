package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Size;

import org.serratec.com.backend.ecommerce.entities.CarrinhoEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProdutoDto {

	@Size(max = 40)
	private String nome;

	@Size(max = 250)
	private String descricao;

	private Double preco;

	private Integer quantidadeEstoque;

	private String categoria;

	private LocalDate dataCadastro = LocalDate.now();

	@JsonIgnore
	private List<CarrinhoEntity> carrinhos;

	@JsonIgnore
	private Integer quantidade;

	private String url;

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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public List<CarrinhoEntity> getCarrinhos() {
		return carrinhos;
	}

	public void setCarrinhos(List<CarrinhoEntity> carrinhos) {
		this.carrinhos = carrinhos;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
