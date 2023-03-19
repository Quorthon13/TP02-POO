package com.escola.enums;

public enum DiaSemana {
	SEGUNDA(1), TERCA(2), QUARTA(3), QUINTA(4), SEXTA(5), SABADO(6), DOMINGO(7);

	private int value;

	DiaSemana(int valor) {
		this.value = valor;
	}

	public int getValue() {
		return value;
	}

	public String obterDiaString() {
		switch (this) {
		case SEGUNDA:
			return "Segunda-feira";
		case TERCA:
			return "Terça-feira";
		case QUARTA:
			return "Quarta-feira";
		case QUINTA:
			return "Quinta-feira";
		case SEXTA:
			return "Sexta-feira";
		case SABADO:
			return "Sábado";
		case DOMINGO:
			return "Domingo";
		default:
			return "";
		}
	}
}