package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.CarrinhoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CarrinhoDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.ProdutoService;
import org.serratec.com.backend.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarrinhoMapper {

	@Autowired
	ProdutoService produtoService;

	@Autowired
	PedidoService pedidoService;

	public CarrinhoEntity toEntity(CarrinhoDto dto) throws EntityNotFoundException {
		CarrinhoEntity entity = new CarrinhoEntity();
		entity.setPreco(dto.getPreco());
		entity.setQuantidade(dto.getQuantidade());
		entity.setProdutos(produtoService.findById(dto.getProduto()));
		entity.setPedidos(pedidoService.findById(dto.getPedido()));

		return entity;
	}

	public CarrinhoDto toDto(CarrinhoEntity entity) {
		CarrinhoDto dto = new CarrinhoDto();
		dto.setPreco(entity.getPreco());
		dto.setQuantidade(entity.getQuantidade());
		dto.setProduto(entity.getProdutos().getId());
		dto.setPedido(entity.getPedidos().getId());

		return dto;
	}
}
