package org.serratec.com.backend.ecommerce.repositories;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.CarrinhoEntity;
import org.serratec.com.backend.ecommerce.entities.PedidoEntity;
import org.serratec.com.backend.ecommerce.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<CarrinhoEntity, Long> {

	List<CarrinhoEntity> findByPedidos(PedidoEntity pedidosEntity);

	List<CarrinhoEntity> findByProdutos(ProdutoEntity produtosEntity);

}
