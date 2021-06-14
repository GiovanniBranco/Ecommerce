package org.serratec.com.backend.ecommerce.enums;

public enum StatusCompra {
	FINALIZADO(0, "Pedido finalizado com sucesso!"), NAO_FINALIZADO(1, "Aguardando confirmação do pedido");

	private final Integer status;
	private final String descricao;

	private StatusCompra(Integer status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}

	public Integer getStatus() {
		return status;
	}

	public String getDescricao() {
		return descricao;
	}

}
