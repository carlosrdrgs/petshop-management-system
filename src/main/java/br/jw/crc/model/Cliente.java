package br.jw.crc.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
	private Long id;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	private Contato contato;
	private List<Cao> caes;

	public void setCaes(List<Cao> caes) {
		this.caes = caes;
	}

	public Cliente() {
		this.contato = new Contato();
		this.caes = new ArrayList<>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return contato.getEmail();
	}
	
	public void setEmail(String email) {
		contato.setEmail(email);
	}
	
	public void setTelefone(String telefone) {
		contato.setTelefone(telefone);
	}
	
	public String getTelefone() {
		return contato.getTelefone();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Cao> getCaes() {
		return caes;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate data) {
		this.dataNascimento = data;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}
	
	public void adicionarCao(Cao cao) {
		this.caes.add(cao);
	}
	
	public void removerCao(Cao cao) {
		this.caes.remove(cao);
	}

	@Override
	public String toString() {
		return String.format("Cliente [id=%s, nome=%s, cpf=%s, dataNascimento=%s, contato=%s, caes=%s]", id, nome, cpf,
				dataNascimento, contato, caes);
	}
}

class Contato {
	private String email;
	private String telefone;
	
	public Contato() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return "Contato [email=" + email + ", telefone=" + telefone + "]";
	}
}
