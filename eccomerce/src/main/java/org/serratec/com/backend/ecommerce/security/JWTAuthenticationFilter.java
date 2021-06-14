package org.serratec.com.backend.ecommerce.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authentificationManager;
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authentificationManager, JWTUtil jwtUtil) {
		super();
		this.authentificationManager = authentificationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			ClienteEntity cliente = new ObjectMapper().readValue(req.getInputStream(), ClienteEntity.class);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					cliente.getUsername(), cliente.getSenha(), new ArrayList<>());
			Authentication auth = authentificationManager.authenticate(authToken);

			return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((UserSS) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);

		response.addHeader("Authorization", "Bearer " + token);

	}

}
