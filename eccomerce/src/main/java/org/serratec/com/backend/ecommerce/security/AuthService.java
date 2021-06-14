package org.serratec.com.backend.ecommerce.security;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ClienteEntity cliente = clienteRepository.findByUsername(username);
		if (cliente == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return new UserSS(cliente.getId(), cliente.getUsername(), cliente.getSenha());
	}

}
