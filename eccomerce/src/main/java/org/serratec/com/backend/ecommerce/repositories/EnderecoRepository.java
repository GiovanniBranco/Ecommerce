package org.serratec.com.backend.ecommerce.repositories;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

	List<EnderecoEntity> findByCliente(ClienteEntity clienteEntity);

	EnderecoEntity findByCepAndClienteIdAndNumeroAndComplemento(String cep, Long id, String numero, String complemento);
}