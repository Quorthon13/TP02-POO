package com.escola.classes;

public class Pessoa {
	private String cpf;
	private String nome;
	private String tel;

	public Pessoa() {
	}

	public Pessoa(String cpf, String nome, String tel) {
		this.cpf = cpf;
		this.nome = nome;
		this.tel = tel;
	}

	public void update(Pessoa p) {
		cpf = p.cpf;
		nome = p.nome;
		tel = p.tel;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String toString(boolean gestor) {
		return "Nome: " + nome + "\n" + (gestor ? ("CPF: " + cpf + "\nTelefone: " + tel + "\n") : "");
	}
}
