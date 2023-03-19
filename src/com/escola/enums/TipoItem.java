package com.escola.enums;

public enum TipoItem {
	PROFESSOR("Professor"), ALUNO("Aluno"), SALA("Sala"), CURSO("Curso"), RELATORIOS("Relatórios");

	private String value;

	private TipoItem(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
