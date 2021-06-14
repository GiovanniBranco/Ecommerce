package org.serratec.com.backend.ecommerce.repositories;

import org.serratec.com.backend.ecommerce.entities.ImagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemRepository extends JpaRepository<ImagemEntity, Long> {

	ImagemEntity findByProdutoId(Long id);
}
