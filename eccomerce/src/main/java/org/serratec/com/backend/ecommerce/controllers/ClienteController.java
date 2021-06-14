package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import javax.validation.Valid;

import org.serratec.com.backend.ecommerce.entities.dto.ClienteDto;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.ClienteException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<ClienteSimplesDto>> getAll() {
		return new ResponseEntity<List<ClienteSimplesDto>>(clienteService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{username}")
	public ResponseEntity<ClienteSimplesDto> getById(@PathVariable String username) throws ClienteException {
		return new ResponseEntity<ClienteSimplesDto>(clienteService.getByUsername(username), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ClienteSimplesDto> create(@Valid @RequestBody ClienteDto dto) throws EntityNotFoundException, ClienteException {
		return new ResponseEntity<ClienteSimplesDto>(clienteService.create(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{username}")
	public ResponseEntity<ClienteSimplesDto> update(@PathVariable String username, @Valid @RequestBody ClienteDto dto)
			throws EntityNotFoundException {
		return new ResponseEntity<ClienteSimplesDto>(clienteService.update(username, dto), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<String> delete(@PathVariable String username) throws EntityNotFoundException, ClienteException {
		clienteService.delete(username);
		return new ResponseEntity<String>("Cliente " + username +" deletado com sucesso!", HttpStatus.OK);
	}
}