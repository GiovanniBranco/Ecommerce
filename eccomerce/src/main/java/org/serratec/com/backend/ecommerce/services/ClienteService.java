package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.entities.EnderecoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteDto;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.ClienteException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.ClienteMapper;
import org.serratec.com.backend.ecommerce.mappers.EnderecoMapper;
import org.serratec.com.backend.ecommerce.repositories.ClienteRepository;
import org.serratec.com.backend.ecommerce.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	EnderecoService enderecoService;

	@Autowired
	PedidoService pedidoService;

	@Autowired
	ClienteMapper clienteMapper;

	@Autowired
	EnderecoMapper enderecoMapper;

	@Autowired
	BCryptPasswordEncoder bCrypt;

	public List<ClienteSimplesDto> getAll() {
		return clienteRepository.findAll().stream().map(clienteMapper::toSimplesDto).collect(Collectors.toList());
	}

	public ClienteSimplesDto getByUsername(String username) throws ClienteException {

		if (clienteRepository.findByUsername(username) != null) {
			return clienteMapper.toSimplesDto(clienteRepository.findByUsername(username));
		} else {
			throw new ClienteException("Usuário não existe");
		}
	}

	public ClienteSimplesDto create(ClienteDto clienteDto) throws EntityNotFoundException, ClienteException {
		if (this.findByCpf(clienteDto.getCpf()) != null
				|| clienteRepository.findByEmail(clienteDto.getEmail()).size() != 0
				|| clienteRepository.findByUsername(clienteDto.getUsername()) != null) {
			throw new ClienteException("CPF ou email ou username já cadastrado");
		} else {
			ClienteEntity clienteEntity = clienteMapper.toEntity(clienteDto);
			clienteEntity.setSenha(bCrypt.encode(clienteDto.getSenha()));
			ClienteEntity savedClienteEntity = clienteRepository.save(clienteEntity);

			List<EnderecoEntity> listaEnderecosEntity = enderecoMapper.listaSimplficadaToEntity(enderecoService
					.criarPeloCliente(enderecoMapper.listToDto(clienteDto.getEnderecos()), savedClienteEntity.getId()));
			savedClienteEntity.setEnderecos(listaEnderecosEntity);

			return clienteMapper.toSimplesDto(savedClienteEntity);
		}
	}

	public ClienteSimplesDto update(String username, ClienteDto clienteUpdate) throws EntityNotFoundException {
		ClienteEntity clienteEntity = clienteRepository.findByUsername(username);

		clienteEntity.setUsername(clienteUpdate.getUsername());
		clienteEntity.setSenha(clienteUpdate.getSenha());
		clienteEntity.setNome(clienteUpdate.getNome());
		clienteEntity.setCpf(clienteUpdate.getCpf());
		clienteEntity.setTelefone(clienteUpdate.getTelefone());
		clienteEntity.setDataNascimento(clienteUpdate.getDataNascimento());
		enderecoService.update(clienteEntity.getUsername(), enderecoMapper.listToDto(clienteUpdate.getEnderecos()));

		ClienteDto dto = clienteMapper.toDto(clienteRepository.save(clienteEntity));
		dto.setEnderecos(enderecoRepository.findByCliente(clienteEntity));

		return clienteMapper.toSimplesDto(clienteMapper.toEntity(dto));
	}

	public void delete(String username) throws EntityNotFoundException, ClienteException {

		if (clienteRepository.findByUsername(username) != null) {
			ClienteEntity cliente = clienteRepository.findByUsername(username);
			if (pedidoService.getByCliente(this.findByCpf(cliente.getCpf())).isEmpty()) {
				Long id = this.findByCpf(cliente.getCpf()).getId();
				enderecoService.deleteAll(this.findById(id));
				clienteRepository.deleteById(id);
			} else {
				throw new ClienteException("O cliente " + username + " possui pedidos vinculados. Favor verificar");
			}
		} else {
			throw new EntityNotFoundException("O cliente " + username + " não existe");
		}
	}

	public ClienteEntity findById(Long id) throws EntityNotFoundException {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Cliente com id: " + id + " não encontrado."));
	}

	public ClienteEntity findByCpf(String cpf) {
		return clienteRepository.findByCpf(cpf);
	}

}