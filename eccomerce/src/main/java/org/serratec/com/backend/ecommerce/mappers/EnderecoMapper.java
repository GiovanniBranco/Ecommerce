package org.serratec.com.backend.ecommerce.mappers;

import java.util.ArrayList;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.EnderecoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CadastroUsuarioDto;
import org.serratec.com.backend.ecommerce.entities.dto.EnderecoDto;
import org.serratec.com.backend.ecommerce.entities.dto.EnderecoSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

	public EnderecoEntity toEntity(EnderecoDto dto) {
		EnderecoEntity entity = new EnderecoEntity();
		entity.setCep(dto.getCep());
		entity.setRua(dto.getLogradouro());
		entity.setBairro(dto.getBairro());
		entity.setCidade(dto.getLocalidade());
		entity.setNumero(dto.getNumero());
		entity.setComplemento(dto.getComplemento());
		entity.setEstado(dto.getUf());
		entity.setCliente(dto.getCliente());

		return entity;
	}

	public EnderecoDto toDto(EnderecoEntity entity) {
		EnderecoDto dto = new EnderecoDto();
		dto.setCep(entity.getCep());
		dto.setLogradouro(entity.getRua());
		dto.setBairro(entity.getBairro());
		dto.setLocalidade(entity.getCidade());
		dto.setNumero(entity.getNumero());
		dto.setComplemento(entity.getComplemento());
		dto.setUf(entity.getEstado());
		dto.setCliente(entity.getCliente());

		return dto;
	}

	public EnderecoSimplesDto toSimplificadoDto(EnderecoEntity entity) {
		EnderecoSimplesDto dto = new EnderecoSimplesDto();
		dto.setCep(entity.getCep());
		dto.setLogradouro(entity.getRua());
		dto.setBairro(entity.getBairro());
		dto.setLocalidade(entity.getCidade());
		dto.setNumero(entity.getNumero());
		dto.setComplemento(entity.getComplemento());
		dto.setUf(entity.getEstado());
		dto.setNomeCliente(entity.getCliente().getNome());

		return dto;
	}

	public EnderecoEntity simplesDtoToEntity(EnderecoSimplesDto dto) {
		EnderecoEntity entity = new EnderecoEntity();
		entity.setCep(dto.getCep());
		entity.setRua(dto.getLogradouro());
		entity.setBairro(dto.getBairro());
		entity.setCidade(dto.getLocalidade());
		entity.setNumero(dto.getNumero());
		entity.setComplemento(dto.getComplemento());
		entity.setEstado(dto.getUf());

		return entity;
	}

	public CadastroUsuarioDto entityToCadastro(EnderecoEntity entity) {
		CadastroUsuarioDto dto = new CadastroUsuarioDto();
		dto.setBairro(entity.getBairro());
		dto.setCep(entity.getCep());
		dto.setComplemento(entity.getComplemento());
		dto.setLocalidade(entity.getCidade());
		dto.setLogradouro(entity.getRua());
		dto.setNumero(entity.getNumero());
		dto.setUf(entity.getEstado());

		return dto;
	}

	public List<EnderecoSimplesDto> toListaSimplficadoDto(List<EnderecoEntity> listaEntity) {
		List<EnderecoSimplesDto> listaDto = new ArrayList<>();

		for (EnderecoEntity entity : listaEntity) {
			EnderecoSimplesDto dto = this.toSimplificadoDto(entity);
			listaDto.add(dto);
		}

		return listaDto;
	}

	public List<CadastroUsuarioDto> toListCadastroDto(List<EnderecoEntity> enderecos) {
		List<CadastroUsuarioDto> lista = new ArrayList<>();

		for (EnderecoEntity enderecoEntity : enderecos) {
			CadastroUsuarioDto dto = this.entityToCadastro(enderecoEntity);
			lista.add(dto);
		}

		return lista;
	}

	public List<EnderecoDto> listToDto(List<EnderecoEntity> listaEntity) {
		List<EnderecoDto> listaDto = new ArrayList<>();

		for (EnderecoEntity entity : listaEntity) {
			EnderecoDto dto = this.toDto(entity);
			listaDto.add(dto);
		}

		return listaDto;
	}

	public List<EnderecoEntity> listToEntity(List<EnderecoDto> listaDto) throws EntityNotFoundException {
		List<EnderecoEntity> listaEntity = new ArrayList<>();

		for (EnderecoDto dto : listaDto) {
			EnderecoEntity entity = this.toEntity(dto);
			listaEntity.add(entity);
		}

		return listaEntity;
	}

	public List<EnderecoEntity> listaSimplficadaToEntity(List<EnderecoSimplesDto> listaDto) {
		List<EnderecoEntity> listaEntity = new ArrayList<>();

		for (EnderecoSimplesDto dto : listaDto) {
			EnderecoEntity entity = this.simplesDtoToEntity(dto);
			listaEntity.add(entity);
		}

		return listaEntity;
	}

}
