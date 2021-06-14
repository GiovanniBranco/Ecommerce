package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;
import java.util.List;

import org.serratec.com.backend.ecommerce.enums.StatusCompra;

public class PedidoFinalizadoDto {

	private String numeroPedido;

	private LocalDate dataPedido;

	private Long cliente;

	private List<ProdutosPedidosDto> produto;

	private Double valorTotal;

	private StatusCompra status;

	private LocalDate dataEntrega;

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public List<ProdutosPedidosDto> getProduto() {
		return produto;
	}

	public void setProduto(List<ProdutosPedidosDto> produto) {
		this.produto = produto;
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

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

}
