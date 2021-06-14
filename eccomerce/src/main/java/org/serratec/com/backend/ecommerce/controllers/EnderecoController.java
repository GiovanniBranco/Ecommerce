package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import javax.validation.Valid;

import org.serratec.com.backend.ecommerce.entities.dto.EnderecoDto;
import org.serratec.com.backend.ecommerce.entities.dto.EnderecoSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.DataIntegrityViolationException;
import org.serratec.com.backend.ecommerce.exceptions.EnderecoRepetidoException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.EnderecoService;
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
@RequestMapping("/endereco")
public class EnderecoController {

	@Autowired
	EnderecoService enderecoService;

	@GetMapping
	public ResponseEntity<List<EnderecoSimplesDto>> getAll() {
		return new ResponseEntity<List<EnderecoSimplesDto>>(enderecoService.getAll(), HttpStatus.OK);
	}

	@PostMapping("/{username}")
	public ResponseEntity<EnderecoSimplesDto> create(@Valid @RequestBody EnderecoDto dto, @PathVariable String username)
			throws EntityNotFoundException, EnderecoRepetidoException {
		return new ResponseEntity<EnderecoSimplesDto>(enderecoService.create(dto, username), HttpStatus.CREATED);
	}

	@PutMapping("/{username}")
	public ResponseEntity<List<EnderecoDto>> update(@PathVariable String username,
			@Valid @RequestBody List<EnderecoDto> dto) throws EntityNotFoundException {
		return new ResponseEntity<List<EnderecoDto>>(enderecoService.update(username, dto), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id)
			throws EntityNotFoundException, DataIntegrityViolationException {
		enderecoService.delete(id);
		return new ResponseEntity<String>("Endereço deletado com sucesso!", HttpStatus.OK);
	}

}
