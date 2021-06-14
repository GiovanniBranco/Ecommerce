package org.serratec.com.backend.ecommerce.repositories;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>{
	
	public List<ProdutoEntity> findByCategoriaId(Long idCategoria);	
	
	public ProdutoEntity findByNome(String nome);

}
