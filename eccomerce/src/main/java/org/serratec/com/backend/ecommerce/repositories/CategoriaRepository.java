package org.serratec.com.backend.ecommerce.repositories;

import org.serratec.com.backend.ecommerce.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
	public CategoriaEntity findByNome(String nome);
}
