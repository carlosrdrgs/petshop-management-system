package br.jw.crc.model;

public class Administrator {
	private int id;
	private String login;
	private String senha;
	
	public Administrator() {
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Administrator [id=" + id + ", login=" + login + ", senha=" + senha + "]";
	}
}
