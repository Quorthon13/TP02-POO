package com.escola.classes;

public class Gestor extends Pessoa {
	private String usuario;
	private String senha;

	public Gestor() {
	}

	public Gestor(String cpf, String nome, String tel, String usuario, String senha) {
		super(cpf, nome, tel);
		this.usuario = usuario;
		this.senha = senha;
	}

	// retorna true se o usuário e senha forem iguais ao do gestor
	public boolean login(String usuario, String senha) {
		return this.usuario.equals(usuario) && this.senha.equals(senha);
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
