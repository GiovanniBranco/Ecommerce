package org.serratec.com.backend.ecommerce.controllers;

import java.io.IOException;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.ImagemEntity;
import org.serratec.com.backend.ecommerce.entities.ProdutoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutoDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.serratec.com.backend.ecommerce.services.ImagemService;
import org.serratec.com.backend.ecommerce.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoService produtoService;

	@Autowired
	ImagemService imagemService;

	@GetMapping
	public ResponseEntity<List<ProdutoDto>> getAll() {
		return new ResponseEntity<List<ProdutoDto>>(produtoService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDto> getById(@PathVariable Long id) throws EntityNotFoundException {
		return new ResponseEntity<ProdutoDto>(produtoService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/categoria/{nome}")
	public ResponseEntity<List<ProdutoDto>> getByProdutoByNomeCategoria(@PathVariable String nome)
			throws EntityNotFoundException {
		return new ResponseEntity<List<ProdutoDto>>(produtoService.getByCategoriaNome(nome), HttpStatus.OK);
	}

	@GetMapping("/nome")
	public ResponseEntity<ProdutoDto> getByName(@RequestParam String nome) throws EntityNotFoundException {
		return new ResponseEntity<ProdutoDto>(produtoService.getByName(nome), HttpStatus.OK);
	}

	@GetMapping("/{nomeProduto}/imagem")
	public ResponseEntity<byte[]> getImage(@PathVariable String nomeProduto) throws EntityNotFoundException {
		ProdutoEntity produto = produtoService.findByName(nomeProduto.toLowerCase());
		ImagemEntity imagem = imagemService.getImagem(produto.getId());
		HttpHeaders header = new HttpHeaders();
		header.add("content-length", String.valueOf(imagem.getData().length));
		header.add("content-type", imagem.getMimeType());
		return new ResponseEntity<byte[]>(imagemService.getImagem(produto.getId()).getData(), header, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ProdutoDto> create(@RequestParam MultipartFile file, @RequestPart ProdutoDto product)
			throws EntityNotFoundException, ProdutoException, IOException {
		return new ResponseEntity<ProdutoDto>(produtoService.create(product, file), HttpStatus.CREATED);
	}

	@PutMapping("/{nomeProduto}")
	public ResponseEntity<ProdutoDto> update(@PathVariable String nomeProduto, @RequestBody ProdutoDto produto)
			throws EntityNotFoundException, ProdutoException {
		return new ResponseEntity<ProdutoDto>(produtoService.update(nomeProduto, produto), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{nomeProduto}")
	public ResponseEntity<String> delete(@PathVariable String nomeProduto)
			throws EntityNotFoundException, ProdutoException {
		produtoService.delete(nomeProduto);

		return new ResponseEntity<String>("Produto com nome " + nomeProduto + " deletado com sucesso!", HttpStatus.OK);
	}
}
