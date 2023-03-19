package com.escola.enums;

public enum OperacaoEnum {
	CADASTRAR("Cadastrar"), ATUALIZAR("Atualizar"), EXCLUIR("Excluir"), CONSULTAR("Consultar");

	private String value;

	private OperacaoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
