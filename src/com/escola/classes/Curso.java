package com.escola.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.escola.enums.DiaSemana;
import com.escola.enums.TipoItem;
import com.escola.telas.TelaCadastroCurso;
import com.escola.telas.TelaUpdate;
import com.escola.util.UIUtils;

public class Curso implements Crud {
	private String codigo;
	private String nome;
	private Professor prof;
	private int numAulas;
	private List<Aluno> alunos;

	// máximo de aulas por curso
	public static final int MAX_AULAS = 2;

	public Curso() {
		this.alunos = new ArrayList<Aluno>();
		this.prof = null;
		this.numAulas = 0;
	}

	public Curso(String codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
		this.alunos = new ArrayList<Aluno>();
		this.prof = null;
		this.numAulas = 0;
	}

	public Curso(String codigo, String nome, Professor prof) {
		this.codigo = codigo;
		this.nome = nome;
		this.alunos = new ArrayList<Aluno>();
		this.prof = prof;
		this.numAulas = 0;
	}

	public static Curso selecionarCurso(Escola e) {
		final Curso selecionado = new Curso();

		JDialog dialog = UIUtils.createEmptyDialog("Selecionar curso");
		JLabel cursoLabel = new JLabel("Selecione o curso: ");
		JComboBox<Curso> cursoBox = new JComboBox<Curso>();
		for (Curso curso : e.getCursos()) {
			cursoBox.addItem(curso);
		}
		JButton cadastrarButton = new JButton("Selecionar");
		cadastrarButton.addActionListener(event -> {
			selecionado.update((Curso) cursoBox.getSelectedItem());
			dialog.dispose();
		});
		UIUtils.addComponent(dialog, cursoLabel, 0, 0);
		UIUtils.addComponent(dialog, cursoBox, 1, 0);
		UIUtils.addComponent(dialog, cadastrarButton, 1, 1);
		UIUtils.showDialog(dialog);
		if (selecionado.codigo != null) {
			return e.getCursos().stream().filter(c -> c.codigo.equals(selecionado.codigo)).findAny().get();
		}
		return null;
	}

	public static void ordenarCursos(List<Curso> cursos) {
		Comparator<Curso> comparator = new Comparator<Curso>() {
			public int compare(Curso a, Curso b) {
				return Integer.compare(b.getAlunos().size(), a.getAlunos().size());
			}
		};
		Collections.sort(cursos, comparator);
	}

	public static void cadastrar(Escola e) {
		Curso curso = new TelaCadastroCurso(e).getCurso();
		if (curso == null)
			return;
		if (verificarCursoJaCadastrado(e, curso)) {
			JOptionPane.showMessageDialog(null, "Curso " + curso.codigo + " já cadastrado.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		e.getCursos().add(curso);
		if (!e.distribuirSalas()) {
			e.getCursos().remove(e.getCursos().size() - 1);
			e.distribuirSalas();
			JOptionPane.showMessageDialog(null, "Não foi possível distribuir todas as salas.", "Erro",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Curso " + curso.codigo + " cadastrado.", "Cadastro",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// verifica se o curso ja foi cadastrado
	private static boolean verificarCursoJaCadastrado(Escola e, Curso curso) {
		return e.getCursos().stream().filter(c -> c.codigo.equals(curso.codigo)).findAny().isPresent();
	}

	public static void atualizar(Escola e) {
		Integer opcao = new TelaUpdate(TipoItem.CURSO, Arrays.asList("Alterar dados do curso")).getSelectedButton();

		if (opcao == null) {
			return;
		}

		switch (opcao) {
		case 0:
			alterarDadosCurso(e);
			break;
		}
	}

	// altera os dados cadastrais do curso
	private static void alterarDadosCurso(Escola e) {
		Curso selecionado = selecionarCurso(e);
		if (selecionado == null) {
			return;
		}

		Curso novo = (new TelaCadastroCurso(e)).getCurso();
		if (novo == null)
			return;

		selecionado.update(novo);
		JOptionPane.showMessageDialog(null, "Curso atualizado.", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void excluir(Escola e) {
		Curso curso = selecionarCurso(e);
		if (curso == null) {
			return;
		}
		// remove curso dos alunos
		for (Aluno a : curso.alunos) {
			a.getCursos().removeIf(c -> c.equals(curso));
		}
		// remove curso do professor
		if (curso.prof != null) {
			curso.prof.getCursos().removeIf(c -> c.equals(curso));
		}

		// remove curso da escola
		e.getCursos().removeIf(c -> c.equals(curso));

		e.distribuirSalas();

		JOptionPane.showMessageDialog(null, "Curso " + curso + " removido.", "Excluir",
				JOptionPane.INFORMATION_MESSAGE);

	}

	public static void consultar(Escola e) {
		Curso c = selecionarCurso(e);
		if (c == null)
			return;

		StringBuilder sb = new StringBuilder();
		sb.append("\n------------------------------------\n").append(c.toString(true)).append("\nHorarios: ");
		int i = 1;
		for (Sala sala : e.getSalas()) {
			for (DiaSemana dia : sala.obterDiasCurso(c)) {
				sb.append("Sala ").append(i).append(" - Dia: ").append(dia.obterDiaString()).append("\n");
			}
			i++;
		}

		UIUtils.createWindow("Consulta Curso", sb.toString());

	}

	private void update(Curso c) {
		codigo = c.codigo;
		nome = c.nome;
		prof = c.prof;
	}

	public void incrementarAulas() {
		numAulas++;
	}

	public int getNumAlunos() {
		return alunos.size();
	}

	public Professor getProfessor() {
		return prof;
	}

	public void setProfessor(Professor prof) {
		this.prof = prof;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public int getNumAulas() {
		return numAulas;
	}

	public void setNumAulas(int numAulas) {
		this.numAulas = numAulas;
	}

	public String toString(boolean gestor) {
		return "Codigo: " + codigo + "\nNome: " + nome + "\nProfessor: " + (prof != null ? prof.getNome() : "") + "\n";
	}

	public String toString() {
		return codigo + " - " + nome;
	}
}
