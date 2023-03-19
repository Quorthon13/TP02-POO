package com.escola.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import com.escola.enums.DiaSemana;
import com.escola.telas.TelaCadastroSala;

public class Sala {
	private int capacidade;

	private ArrayList<Curso> cursos;

	// quantidade máxima de aulas por semana em uma sala
	public static final int QUANT_DIAS = 5;

	public Sala() {
		this.cursos = new ArrayList<Curso>();
		this.capacidade = 0;
	}

	public Sala(int capacidade) {
		this.cursos = new ArrayList<Curso>();
		this.capacidade = capacidade;
	}

	public static void cadastrar(Escola e) {
		// abre a tela de cadastro de sala
		Sala s = new TelaCadastroSala().getSala();
		if (s == null)
			return;

		e.getSalas().add(s);
		// realiza uma nova distribuição de salas
		if (!e.distribuirSalas()) {
			// erro ao distribuir salas
			JOptionPane.showMessageDialog(null, "Não foi possível distribuir todas as salas.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			// remove a sala recém criada
			e.getSalas().remove(e.getSalas().size() - 1);
			// redistribui
			e.distribuirSalas();
		} else {
			JOptionPane.showMessageDialog(null, "Sala " + e.getSalas().size() + " cadastrada.", "Cadastro",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void ordenarSalas(List<Sala> salas) {
		Comparator<Sala> comparator = new Comparator<Sala>() {
			public int compare(Sala a, Sala b) {
				return Integer.compare(b.getCapacidade(), a.getCapacidade());
			}
		};
		Collections.sort(salas, comparator);
	}

	// retorna os dias em que a sala terá esse curso
	public List<DiaSemana> obterDiasCurso(Curso c) {
		List<DiaSemana> dias = new ArrayList<DiaSemana>();
		int i = 1;
		for (Curso curso : cursos) {
			if (curso.getCodigo().equals(c.getCodigo())) {
				dias.add(DiaSemana.values()[i - 1]);
			}
			i++;
		}
		return dias;
	}

	// retorna os dias em que a sala está reservada
	public List<DiaSemana> obterDiasReservados() {
		List<DiaSemana> dias = new ArrayList<DiaSemana>();
		for (int i = 1; i <= cursos.size(); i++) {
			dias.add(DiaSemana.values()[i - 1]);
		}

		return dias;
	}

	// retorna os dias em que a sala está livre
	public List<DiaSemana> obterDiasLivres() {
		List<DiaSemana> dias = new ArrayList<DiaSemana>();

		int i = getNumDias();
		while (i < QUANT_DIAS) {
			dias.add(DiaSemana.values()[(++i) - 1]);
		}

		return dias;
	}

	public void adicionarCurso(Curso curso) {
		cursos.add(curso);
	}

	// returna true se a sala está ocupada nos 5 dias da semana
	public boolean isSalaLotada() {
		return getNumDias() >= QUANT_DIAS;
	}

	public int getNumDias() {
		return cursos.size();
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public ArrayList<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(ArrayList<Curso> cursos) {
		this.cursos = cursos;
	}
}
