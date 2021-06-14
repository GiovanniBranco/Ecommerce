package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.CategoriaEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CategoriaDto;
import org.serratec.com.backend.ecommerce.exceptions.CategoriaException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.CategoriaMapper;
import org.serratec.com.backend.ecommerce.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	CategoriaMapper categoriaMapper;

	@Autowired
	ProdutoService produtoService;

	public CategoriaEntity findById(Long id) throws EntityNotFoundException {
		return categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public CategoriaEntity findByNome(String nome) {
		return categoriaRepository.findByNome(nome);
	}

	public List<CategoriaDto> getAll() {
		return categoriaRepository.findAll().stream().map(categoriaMapper::toDto).collect(Collectors.toList());
	}

	public CategoriaDto getById(Long id) throws EntityNotFoundException {
		return categoriaMapper.toDto(this.findById(id));
	}

	public CategoriaDto getByName(String nome) {
		nome = nome.toLowerCase();
		return categoriaMapper.toDto(categoriaRepository.findByNome(nome));
	}

	public CategoriaDto create(CategoriaDto categoriaDto) throws CategoriaException {
		categoriaDto.setNome(categoriaDto.getNome().toLowerCase());
		if (categoriaDto.getNome().isBlank()) {
			throw new CategoriaException("O nome da categoria é obrigatório");
		}
		if (this.findByNome(categoriaDto.getNome()) != null) {
			throw new CategoriaException("Categoria com o nome: " + categoriaDto.getNome() + " já cadastrada");
		} else {
			categoriaDto.setNome(categoriaDto.getNome().toLowerCase());
			categoriaRepository.save(categoriaMapper.toEntity(categoriaDto));

			return categoriaDto;
		}
	}

	public CategoriaDto update(String nome, CategoriaDto categoriaUpdate)
			throws EntityNotFoundException, CategoriaException {
		CategoriaEntity categoriaEntity = this.findByNome(nome.toLowerCase());
		if (categoriaEntity != null) {
			if (categoriaUpdate.getNome() != null) {
				if (this.findByNome(categoriaUpdate.getNome().toLowerCase()) != null) {
					throw new CategoriaException(
							"Categoria " + categoriaUpdate.getNome().toLowerCase() + " já cadastrada");
				} else {
					if(categoriaUpdate.getNome().isBlank()) {
						throw new CategoriaException("Para se atualizar o nome é necessário informar um valor.");
					}else {						
						categoriaEntity.setNome(categoriaUpdate.getNome());
					}
				}
			}
			if (!categoriaUpdate.getDescricao().isBlank()) {
				categoriaEntity.setDescricao(categoriaUpdate.getDescricao());
			} else if (categoriaUpdate.getDescricao().isBlank()) {
				throw new CategoriaException("Para se atualizar a descrição é necessário informar um valor.");
			}
			return categoriaMapper.toDto(categoriaRepository.save(categoriaEntity));
		} else {
			throw new EntityNotFoundException("Categoria com nome: " + nome.toLowerCase() + " não encontrada");
		}
	}

	public void delete(String nomeCategoria) throws EntityNotFoundException, CategoriaException {
		CategoriaEntity categoria = this.findByNome(nomeCategoria.toLowerCase());
		if (categoria != null) {
			if (produtoService.findByCategoriaId(categoria.getId()).isEmpty()) {
				categoriaRepository.deleteById(categoria.getId());
			} else {
				throw new CategoriaException("Categoria com nome: " + nomeCategoria.toLowerCase()
						+ " já vinculada a um ou mais produtos, favor verificar!");
			}
		} else {
			throw new EntityNotFoundException("Categoria com nome: " + nomeCategoria.toLowerCase() + " não encontrada");
		}
	}
}
