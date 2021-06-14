package org.serratec.com.backend.ecommerce.mappers;

import java.util.ArrayList;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.CategoriaEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CategoriaDto;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
	public CategoriaEntity toEntity(CategoriaDto dto) {
		CategoriaEntity categoria = new CategoriaEntity();
		categoria.setNome(dto.getNome());
		categoria.setDescricao(dto.getDescricao());

		return categoria;
	}

	public CategoriaDto toDto(CategoriaEntity entity) {
		CategoriaDto dto = new CategoriaDto();

		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());

		return dto;
	}

	public List<CategoriaDto> listToDto(List<CategoriaEntity> listaEntity) {
		List<CategoriaDto> listaDto = new ArrayList<>();
		for (CategoriaEntity entity : listaEntity) {
			CategoriaDto dto = this.toDto(entity);
			listaDto.add(dto);
		}
		return listaDto;
	}

	public List<CategoriaEntity> listToEntity(List<CategoriaDto> listaDto) {
		List<CategoriaEntity> listaEntity = new ArrayList<>();
		for (CategoriaDto dto : listaDto) {
			CategoriaEntity entity = this.toEntity(dto);
			listaEntity.add(entity);
		}
		return listaEntity;
	}
}
