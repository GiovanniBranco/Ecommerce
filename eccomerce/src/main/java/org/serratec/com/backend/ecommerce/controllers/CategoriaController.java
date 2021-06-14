package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.dto.CategoriaDto;
import org.serratec.com.backend.ecommerce.exceptions.CategoriaException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<CategoriaDto>> getAll() {
		return new ResponseEntity<List<CategoriaDto>>(categoriaService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{nome}")
	public ResponseEntity<CategoriaDto> getByName(@PathVariable String nome) {
		return new ResponseEntity<CategoriaDto>(categoriaService.getByName(nome), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CategoriaDto> create(@RequestBody CategoriaDto category) throws CategoriaException {
		return new ResponseEntity<CategoriaDto>(categoriaService.create(category), HttpStatus.CREATED);
	}

	@PutMapping("/{nomeCategoria}")
	public ResponseEntity<CategoriaDto> update(@PathVariable String nomeCategoria, @RequestBody CategoriaDto category)
			throws EntityNotFoundException, CategoriaException {
		return new ResponseEntity<CategoriaDto>(categoriaService.update(nomeCategoria, category), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{nomeCategoria}")
	public ResponseEntity<String> delete(@PathVariable String nomeCategoria)
			throws EntityNotFoundException, CategoriaException {
		categoriaService.delete(nomeCategoria);
		return new ResponseEntity<String>("Categoria com nome: " + nomeCategoria + " deletada com sucesso!",
				HttpStatus.OK);
	}
}
