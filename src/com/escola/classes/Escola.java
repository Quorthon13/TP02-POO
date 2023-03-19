package com.escola.classes;

import static com.escola.enums.TipoItem.ALUNO;
import static com.escola.enums.TipoItem.CURSO;
import static com.escola.enums.TipoItem.PROFESSOR;
import static com.escola.enums.TipoItem.SALA;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.escola.enums.DiaSemana;
import com.escola.enums.OperacaoEnum;
import com.escola.enums.TipoItem;
import com.escola.telas.TelaCrud;
import com.escola.telas.TelaRelatorios;
import com.escola.util.UIUtils;

public class Escola {
	private List<Aluno> alunos;
	private List<Professor> professores;
	private List<Curso> cursos;
	private List<Sala> salas;
	private List<Gestor> gestores;

	private static final String PACOTE_CLASSES = "com.escola.classes.";

	public Escola() {

	}

	// handler do tipo de registro selecionado pelo gestor
	public void gerenciar(TipoItem item) {
		if (item == TipoItem.RELATORIOS) {
			while (true) {
				TipoItem relatorio = (new TelaRelatorios(Arrays.asList(ALUNO, PROFESSOR, CURSO, SALA)))
						.getSelectedButton();
				if (relatorio == null) {
					return;
				}
				exibirRelatorio(relatorio, true);
			}
		}

		crud(item);
	}

	// realiza as operações de CRUD
	private void crud(TipoItem item) {
		OperacaoEnum operacao;
		do {
			// Salas possuem apenas cadastro
			if (item == TipoItem.SALA) {
				operacao = OperacaoEnum.CADASTRAR;
			} else {
				// mostra o dialog para seleção da operação
				operacao = (new TelaCrud(item)).getSelectedButton();
				if (operacao == null)
					return;
			}

			/**
			 * utiliza REFLECTION para fazer a chamada das funções de crud nas classes
			 * ALUNO, PROFESSOR, CURSO, SALA
			 **/
			try {
				Class<?> clazz = Class.forName(PACOTE_CLASSES + item.getValue());
				Method m = clazz.getMethod(operacao.getValue().toLowerCase(), Escola.class);
				m.invoke(null, this);
			} catch (Exception e) {
				// erro na reflection
				e.printStackTrace();
			}
		} while (item != SALA);
	}

	public boolean validarLoginGestor(String usuario, String senha) {
		for (Gestor g : gestores) {
			if (g.login(usuario, senha)) {
				return true;
			}
		}
		return false;
	}

	public void exibirRelatorio(TipoItem relatorio, boolean gestor) {
		String texto = "";
		switch (relatorio) {
		case ALUNO:
			texto = emitirRelatorioAluno(gestor);
			break;
		case PROFESSOR:
			texto = emitirRelatorioProfessor(gestor);
			break;
		case CURSO:
			texto = emitirRelatorioCurso(gestor);
			break;
		case SALA:
			texto = emitirRelatorioSala(gestor);
			break;
		default:
			break;
		}
		UIUtils.createWindow("Relatório: " + relatorio.getValue(), texto);
	}

	public String emitirRelatorioAluno(boolean gestor) {
		StringBuilder sb = new StringBuilder();
		for (Aluno a : alunos) {
			sb.append("\n------------------------------------\n").append(a.toString(gestor)).append("Cursos: ");
			for (Curso c : a.getCursos()) {
				sb.append(c.getCodigo()).append(" ");
			}
			sb.append("\n------------------------------------\n");
		}
		return sb.toString();
	}

	public String emitirRelatorioProfessor(boolean gestor) {
		StringBuilder sb = new StringBuilder();
		for (Professor p : professores) {
			sb.append("\n------------------------------------\n").append(p.toString(gestor)).append("\nCursos: ");
			for (Curso c : p.getCursos()) {
				sb.append(c.getCodigo()).append(" ");
			}
			sb.append("\n------------------------------------\n");
		}
		return sb.toString();
	}

	public String emitirRelatorioCurso(boolean gestor) {
		StringBuilder sb = new StringBuilder();
		for (Curso c : cursos) {
			sb.append("\n------------------------------------\n").append(c.toString(gestor)).append("\nHorarios: ");
			int i = 1;
			for (Sala sala : salas) {
				for (DiaSemana dia : sala.obterDiasCurso(c)) {
					sb.append("Sala ").append(i).append(" - Dia: ").append(dia.obterDiaString()).append("\n");
				}
				i++;
			}
			sb.append("------------------------------------\n");
		}
		return sb.toString();
	}

	public String emitirRelatorioSala(boolean gestor) {
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for (Sala sala : salas) {
			sb.append("\n------------------------------------\n").append("Sala ").append(i).append("\n\nCapacidade: ")
					.append(sala.getCapacidade());
			sb.append("\nCursos: ");
			for (Curso c : sala.getCursos()) {
				sb.append(c.getCodigo()).append(" ");
			}
			sb.append("\nDias livres: ");
			for (DiaSemana dia : sala.obterDiasLivres()) {
				sb.append(dia.obterDiaString()).append(" ");
			}
			sb.append("\nDias reservados: ");
			for (DiaSemana dia : sala.obterDiasReservados()) {
				sb.append(dia.obterDiaString()).append(" ");
			}
			i++;
		}
		return sb.toString();
	}

	public boolean verificarCursoMesmoDia(Curso curso, int dia) {
		for (Sala sala : salas) {
			try {
				Curso c = sala.getCursos().get(dia);
				if (curso.getCodigo() == c.getCodigo()) {
					return true;
				}
			} catch (IndexOutOfBoundsException e) {
				// não fazer nada
			}
		}
		return false;
	}

	/**
	 * retorna o curso com mais alunos que possui menos de MAX_AULAS
	 * 
	 * caso NULL, não há mais cursos a serem distribuidos
	 **/
	private Sala obterMaiorSala(Curso curso) {
		if (salas.isEmpty()) {
			return null;
		}
		// cria uma copia das salas para ordenar
		ArrayList<Sala> vetor = new ArrayList<Sala>(salas);
		Sala.ordenarSalas(vetor);
		while (!vetor.isEmpty()) {
			Sala maior = vetor.get(0);
			if (!maior.isSalaLotada() && !verificarCursoMesmoDia(curso, maior.getNumDias())) {
				return maior;
			}

			// remove a sala invalida do vetor de copia
			vetor.remove(0);
		}
		return null;
	}

	private Curso obterMaiorCurso() {
		if (cursos.isEmpty()) {
			return null;
		}
		// cria uma copia dos cursos para ordenar
		ArrayList<Curso> vetor = new ArrayList<Curso>(cursos);

		Curso.ordenarCursos(vetor);
		while (!vetor.isEmpty()) {
			Curso maior = vetor.get(0);
			// caso tenha pelo menos que MAX_AULAS por semana, retorna maior curso
			if (maior.getNumAulas() < Curso.MAX_AULAS) {
				return maior;
			}

			// remove o curso invalido do vetor de copia
			vetor.remove(0);
		}
		return null;
	}

	private void adicionarCursoEmSala(Curso c, Sala s) {
		c.incrementarAulas();
		s.adicionarCurso(c);
	}

	/*
	 * retorna false se não for possível distribuir todos os cursos durante a semana
	 */
	public boolean distribuirSalas() {
		// se não há cursos ou alunos em cursos, não precisa distribuir
		if (cursos.isEmpty()) {
			return true;
		}

		// se não há salas, não há como distribuir
		if (salas.isEmpty()) {
			return false;
		}

		// limpa as salas para preparar para a distribuição
		esvaziarSalas();
		while (true) {
			Curso curso = obterMaiorCurso();
			// caso NULL, não há mais cursos a serem distribuidos
			if (curso == null) {
				return true;
			}

			Sala sala = obterMaiorSala(curso);
			// caso NULL, não há salas disponíveis para alocacão
			if (sala == null) {
				return false;
			}
			// retorna false caso a maior sala não suporte o maior curso
			if (curso.getNumAlunos() > sala.getCapacidade()) {
				return false;
			}

			adicionarCursoEmSala(curso, sala);
		}
	}

	public void esvaziarSalas() {
		for (Sala sala : salas) {
			sala.getCursos().clear();
		}
		for (Curso curso : cursos) {
			curso.setNumAulas(0);
		}
	}

	// cria uma escola com dados padrão para testes
	public static Escola criarEscolaPadrao() {
		Escola e = new Escola();
		Sala s1 = new Sala(10), s2 = new Sala(5), s3 = new Sala(5), s4 = new Sala(3);
		Curso c1 = new Curso("c1", "c1"), c2 = new Curso("c2", "c2"), c3 = new Curso("c3", "c3"),
				c4 = new Curso("c4", "c4"), c5 = new Curso("c5", "c5"), c6 = new Curso("c6", "c6"),
				c7 = new Curso("c7", "c7"), c8 = new Curso("c8", "c8");
		Gestor g1 = new Gestor("00000000000", "Welinton", "zap", "admin", "admin");
		Aluno a1 = new Aluno("00000000001", "Diego", "994781420", "20.2.4093");
		Professor p1 = new Professor("99999999999", "Elson", "995183910");

		c5.setProfessor(p1);

		List<Curso> cursosP1 = new ArrayList<Curso>();
		cursosP1.add(c5);
		p1.setCursos(cursosP1);

		List<Curso> cursosA1 = new ArrayList<Curso>();
		cursosA1.add(c5);
		a1.setCursos(cursosA1);

		List<Aluno> alunosC5 = new ArrayList<Aluno>();
		alunosC5.add(a1);
		c5.setAlunos(alunosC5);

		List<Sala> salas = new ArrayList<Sala>();
		salas.add(s1);
		salas.add(s2);
		salas.add(s3);
		salas.add(s4);
		e.setSalas(salas);

		List<Curso> cursos = new ArrayList<Curso>();
		cursos.add(c1);
		cursos.add(c2);
		cursos.add(c3);
		cursos.add(c4);
		cursos.add(c5);
		cursos.add(c6);
		cursos.add(c7);
		cursos.add(c8);
		e.setCursos(cursos);

		List<Aluno> alunos = new ArrayList<Aluno>();
		alunos.add(a1);
		e.setAlunos(alunos);

		List<Gestor> gestores = new ArrayList<Gestor>();
		gestores.add(g1);
		e.setGestores(gestores);

		List<Professor> professores = new ArrayList<Professor>();
		professores.add(p1);
		e.setProfessores(professores);

		e.distribuirSalas();
		return e;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Sala> getSalas() {
		return salas;
	}

	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}

	public List<Gestor> getGestores() {
		return gestores;
	}

	public void setGestores(List<Gestor> gestores) {
		this.gestores = gestores;
	}

}
