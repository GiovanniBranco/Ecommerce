package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.PedidoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CadastroPedidoDto;
import org.serratec.com.backend.ecommerce.entities.dto.PedidoDto;
import org.serratec.com.backend.ecommerce.entities.dto.PedidoFinalizadoDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

	@Autowired
	ClienteService clienteService;

	public PedidoEntity toEntity(PedidoDto dto) throws EntityNotFoundException {
		PedidoEntity entity = new PedidoEntity();
		entity.setNumeroPedido(dto.getNumeroPedido());
		entity.setCliente(clienteService.findById(dto.getCliente()));
		entity.setStatus(dto.getStatus());
		entity.setValorTotal(dto.getValorTotal());

		return entity;
	}

	public PedidoDto toDto(PedidoEntity entity) {
		PedidoDto dto = new PedidoDto();
		dto.setNumeroPedido(entity.getNumeroPedido());
		dto.setCliente(entity.getCliente().getId());
		dto.setStatus(entity.getStatus());
		dto.setValorTotal(entity.getValorTotal());;
		return dto;
	}

	public CadastroPedidoDto toCadastroPedidoDto(PedidoDto pedidoDto) {
		CadastroPedidoDto cadastroPedidoDto = new CadastroPedidoDto();
		cadastroPedidoDto.setNumeroPedido(pedidoDto.getNumeroPedido());
		cadastroPedidoDto.setCliente(pedidoDto.getCliente());
		cadastroPedidoDto.setStatus(pedidoDto.getStatus());
		cadastroPedidoDto.setValorTotal(pedidoDto.getValorTotal());
		cadastroPedidoDto.setProdutos(pedidoDto.getProduto());

		return cadastroPedidoDto;
	}

	public PedidoFinalizadoDto toPedidoFinalizadoDto(PedidoEntity pedido) {
		PedidoFinalizadoDto dto = new PedidoFinalizadoDto();
		dto.setNumeroPedido(pedido.getNumeroPedido());
		dto.setDataPedido(pedido.getDataPedido());
		dto.setDataEntrega(pedido.getDataEntrega());
		dto.setCliente(pedido.getCliente().getId());
		dto.setStatus(pedido.getStatus());
		dto.setValorTotal(pedido.getValorTotal());

		return dto;
	}

}
