package org.serratec.com.backend.ecommerce.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.entities.EnderecoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.EnderecoDto;
import org.serratec.com.backend.ecommerce.entities.dto.EnderecoSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.EnderecoRepetidoException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.EnderecoMapper;
import org.serratec.com.backend.ecommerce.repositories.ClienteRepository;
import org.serratec.com.backend.ecommerce.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	EnderecoMapper enderecoMapper;

	@Autowired
	ClienteService clienteService;

	@Autowired
	ClienteRepository clienteRepository;

	public List<EnderecoSimplesDto> getAll() {
		return enderecoMapper.toListaSimplficadoDto(enderecoRepository.findAll());
	}

	public EnderecoEntity findById(Long id) throws EntityNotFoundException {
		return enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public EnderecoSimplesDto getById(Long id) throws EntityNotFoundException {
		return enderecoMapper.toSimplificadoDto(this.findById(id));
	}

	public EnderecoSimplesDto create(EnderecoDto enderecoNovo, String username)
			throws EntityNotFoundException, EnderecoRepetidoException {

		ClienteEntity cliente = clienteRepository.findByUsername(username);
		List<EnderecoEntity> listaEnderecos = cliente.getEnderecos();
		EnderecoDto dto = this.setEndereco(enderecoNovo, cliente.getId());

		if (listaEnderecos.size() > 0) {
			for (EnderecoEntity enderecoEntity : listaEnderecos) {
				if (enderecoEntity.getCep().equals(enderecoNovo.getCep())) {

					if (dto.getNumero().equals(enderecoEntity.getNumero())
							&& dto.getComplemento().equals(enderecoEntity.getComplemento())) {
						throw new EnderecoRepetidoException("Endereco Repetido");
					} else {
						if ((enderecoRepository.findByCepAndClienteIdAndNumeroAndComplemento(dto.getCep(),
								cliente.getId(), dto.getNumero(), dto.getComplemento()) == null)) {
							enderecoRepository.save(enderecoMapper.toEntity(dto));
						}
					}

				}
			}
		} else {
			enderecoRepository.save(enderecoMapper.toEntity(dto));
		}

		return enderecoMapper.toSimplificadoDto(enderecoRepository.findByCepAndClienteIdAndNumeroAndComplemento(
				enderecoNovo.getCep(), cliente.getId(), enderecoNovo.getNumero(), enderecoNovo.getComplemento()));
	}

	public List<EnderecoSimplesDto> criarPeloCliente(List<EnderecoDto> listaEnderecoDto, Long idCliente)
			throws EntityNotFoundException {

		for (EnderecoDto enderecoDto : listaEnderecoDto) {

			if (enderecoRepository.findByCepAndClienteIdAndNumeroAndComplemento(enderecoDto.getCep(), idCliente,
					enderecoDto.getNumero(), enderecoDto.getComplemento()) == null) {

				EnderecoDto endDto = this.setEndereco(enderecoDto, idCliente);
				enderecoRepository.save(enderecoMapper.toEntity(endDto));
			}
		}

		enderecoMapper.toListaSimplficadoDto(enderecoRepository.findByCliente(clienteService.findById(idCliente)));

		return enderecoMapper
				.toListaSimplficadoDto(enderecoRepository.findByCliente(clienteService.findById(idCliente)));
	}

	public List<EnderecoDto> update(String username, List<EnderecoDto> enderecoAtualizado) // terminar update
			throws EntityNotFoundException {

		ClienteEntity cliente = clienteRepository.findByUsername(username);
		List<EnderecoEntity> listaEnderecos = cliente.getEnderecos();

		if (enderecoAtualizado.size() > 0) {
			for (EnderecoDto dto : enderecoAtualizado) {

				if (listaEnderecos.size() > 0) {

					for (EnderecoEntity enderecoEntity : listaEnderecos) {

						if (enderecoRepository.findByCepAndClienteIdAndNumeroAndComplemento(dto.getCep(),
								cliente.getId(), dto.getNumero(), dto.getComplemento()) == null) {
							EnderecoDto enderecoDto = this.getCep(dto.getCep());
							enderecoDto.setComplemento(dto.getComplemento());
							enderecoDto.setCliente(cliente);
							enderecoDto.setNumero(dto.getNumero());

							enderecoRepository.save(enderecoMapper.toEntity(enderecoDto));
						}

						if (enderecoEntity.getCep().equals(dto.getCep())) {

							if ((enderecoRepository.findByCepAndClienteIdAndNumeroAndComplemento(dto.getCep(),
									cliente.getId(), dto.getNumero(), dto.getComplemento()) == null)) {
								enderecoEntity.setComplemento(dto.getComplemento());
								enderecoEntity.setNumero(dto.getNumero());
								enderecoRepository.save(enderecoEntity);
							}
						}
					}

				} else {
					EnderecoDto enderecoDto = this.getCep(dto.getCep());
					enderecoDto.setComplemento(dto.getComplemento());
					enderecoDto.setNumero(dto.getNumero());
					enderecoDto.setCliente(cliente);

					enderecoRepository.save(enderecoMapper.toEntity(enderecoDto));
				}
			}
		}

		return enderecoMapper.listToDto(enderecoRepository.findByCliente(cliente));

	}

	public void delete(Long id) throws EntityNotFoundException {

		if (this.findById(id) != null) {
			enderecoRepository.deleteById(id);
		}

	}

	public void deleteAll(ClienteEntity clienteEntity) {
		List<EnderecoEntity> enderecosEntity = enderecoRepository.findByCliente(clienteEntity);

		for (EnderecoEntity enderecoEntity : enderecosEntity) {
			enderecoRepository.delete(enderecoEntity);
		}

	}

	public EnderecoDto setEndereco(EnderecoDto enderecoDto, Long idCliente) throws EntityNotFoundException {
		EnderecoDto endDto = this.getCep(enderecoDto.getCep());
		endDto.setNumero(enderecoDto.getNumero());
		endDto.setComplemento(enderecoDto.getComplemento());
		endDto.setCliente(clienteService.findById(idCliente));

		return endDto;
	}

	private EnderecoDto getCep(@PathVariable(name = "cep") String cep) {
		RestTemplate restTemplate = new RestTemplate();

		String uri = "http://viacep.com.br/ws/{cep}/json/";

		Map<String, String> params = new HashMap<String, String>();
		params.put("cep", cep);

		EnderecoDto endereco = restTemplate.getForObject(uri, EnderecoDto.class, params);

		return endereco;
	}
}
