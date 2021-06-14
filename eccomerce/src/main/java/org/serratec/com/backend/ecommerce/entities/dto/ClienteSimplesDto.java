package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;
import java.util.List;

public class ClienteSimplesDto {

	private String email;
	private String username;
	private String nome;
	private String cpf;
	private String telefone;
	private LocalDate dataNascimento;
	private List<CadastroUsuarioDto> enderecos;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<CadastroUsuarioDto> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<CadastroUsuarioDto> enderecos) {
		this.enderecos = enderecos;
	}

}
