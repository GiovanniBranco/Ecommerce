package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteDto;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteSimplesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

	@Autowired
	EnderecoMapper enderecoMapper;

	public ClienteEntity toEntity(ClienteDto dto) {
		ClienteEntity entity = new ClienteEntity();
		entity.setEmail(dto.getEmail());
		entity.setUsername(dto.getUsername());
		entity.setSenha(dto.getSenha());
		entity.setNome(dto.getNome());
		entity.setCpf(dto.getCpf());
		entity.setTelefone(dto.getTelefone());
		entity.setDataNascimento(dto.getDataNascimento());
		entity.setEnderecos(dto.getEnderecos());

		return entity;
	}

	public ClienteDto toDto(ClienteEntity entity) {
		ClienteDto dto = new ClienteDto();
		dto.setEmail(entity.getEmail());
		dto.setUsername(entity.getUsername());
		dto.setSenha(entity.getSenha());
		dto.setNome(entity.getNome());
		dto.setCpf(entity.getCpf());
		dto.setTelefone(entity.getTelefone());
		dto.setDataNascimento(entity.getDataNascimento());
		dto.setEnderecos(entity.getEnderecos());

		return dto;
	}

	public ClienteSimplesDto toSimplesDto(ClienteEntity entity) {
		ClienteSimplesDto dto = new ClienteSimplesDto();
		dto.setEmail(entity.getEmail());
		dto.setUsername(entity.getUsername());
		dto.setNome(entity.getNome());
		dto.setCpf(entity.getCpf());
		dto.setTelefone(entity.getTelefone());
		dto.setDataNascimento(entity.getDataNascimento());
		dto.setEnderecos(enderecoMapper.toListCadastroDto(entity.getEnderecos()));

		return dto;
	}
}
