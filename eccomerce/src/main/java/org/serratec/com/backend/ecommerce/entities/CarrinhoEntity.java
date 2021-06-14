package org.serratec.com.backend.ecommerce.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUTO_PEDIDO")
public class CarrinhoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double preco;

	private Integer quantidade;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private ProdutoEntity produtos;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private PedidoEntity pedidos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public ProdutoEntity getProdutos() {
		return produtos;
	}

	public void setProdutos(ProdutoEntity produtos) {
		this.produtos = produtos;
	}

	public PedidoEntity getPedidos() {
		return pedidos;
	}

	public void setPedidos(PedidoEntity pedidos) {
		this.pedidos = pedidos;
	}

}
