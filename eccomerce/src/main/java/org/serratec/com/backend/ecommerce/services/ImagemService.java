package org.serratec.com.backend.ecommerce.services;

import java.io.IOException;

import javax.transaction.Transactional;

import org.serratec.com.backend.ecommerce.entities.ImagemEntity;
import org.serratec.com.backend.ecommerce.entities.ProdutoEntity;
import org.serratec.com.backend.ecommerce.repositories.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagemService {

	@Autowired
	ImagemRepository imagemRepository;

	@Transactional
	public ImagemEntity create(ProdutoEntity produtoEntity, MultipartFile file) throws IOException {
		ImagemEntity imagemEntity = new ImagemEntity();
		imagemEntity.setProduto(produtoEntity);
		imagemEntity.setData(file.getBytes());
		imagemEntity.setMimeType(file.getContentType());
		imagemEntity.setNome("Imagem");
		return imagemRepository.save(imagemEntity);
	}

	@Transactional
	public ImagemEntity getImagem(Long id) {
		ImagemEntity imagemEntity = imagemRepository.findByProdutoId(id);
		return imagemEntity;
	}

	@Transactional
	public void deletarImagemProduto(Long id) {
		ImagemEntity imagemEntity = imagemRepository.findByProdutoId(id);
		imagemRepository.delete(imagemEntity);
	}
}
