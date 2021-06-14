package org.serratec.com.backend.ecommerce.repositories;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
	
	ClienteEntity findByCpf(String cpf);

	List<ClienteEntity> findByEmail(String email);

	ClienteEntity findByUsername(String username);
}
