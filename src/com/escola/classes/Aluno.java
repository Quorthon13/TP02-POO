package com.escola.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.escola.enums.TipoItem;
import com.escola.telas.TelaCadastroAluno;
import com.escola.telas.TelaCadastroPessoa;
import com.escola.telas.TelaUpdate;
import com.escola.util.UIUtils;

public class Aluno extends Pessoa implements Crud {
	private String matricula;

	private List<Curso> cursos;

	public Aluno() {
	}

	public Aluno(String cpf, String nome, String tel, String matricula) {
		super(cpf, nome, tel);
		this.matricula = matricula;
		this.cursos = new ArrayList<Curso>();
	}

	public static void cadastrar(Escola e) {
		Aluno aluno = (new TelaCadastroAluno(e)).getAluno();
		if (aluno != null) {
			if (verificarMatriculaJaUtilizada(e, aluno)) {
				JOptionPane.showMessageDialog(null, "Matricula " + aluno.getMatricula() + " já cadastrada.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			} else {
				e.getAlunos().add(aluno);
				JOptionPane.showMessageDialog(null, "Aluno " + aluno.getNome() + " cadastrado.", "Cadastro",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	// verifica se ja existe uma matricula identica no sistema
	private static boolean verificarMatriculaJaUtilizada(Escola e, Aluno aluno) {
		return e.getAlunos().stream().filter(a -> a.getMatricula().equals(aluno.getMatricula())).findAny().isPresent();
	}

	public static void atualizar(Escola e) {
		Integer opcao = new TelaUpdate(TipoItem.ALUNO,
				Arrays.asList("Alterar dados pessoais", "Matricular em curso", "Limpar cursos")).getSelectedButton();

		if (opcao == null) {
			return;
		}

		switch (opcao) {
		case 0:
			alterarDadosPessoais(e);
			break;
		case 1:
			matricularEmCurso(e);
			break;
		case 2:
			limparCursos(e);
			break;
		}
	}

	public static void excluir(Escola e) {
		Aluno aluno = selecionarAluno(e);
		if (aluno == null)
			return;

		for (Curso c : e.getCursos()) {
			c.getAlunos().removeIf(a -> a.equals(aluno));
		}
		e.getAlunos().removeIf(a -> a.equals(aluno));

		e.distribuirSalas();

		JOptionPane.showMessageDialog(null, "Aluno " + aluno + " removido.", "Excluir",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void consultar(Escola e) {
		Aluno aluno = selecionarAluno(e);
		if (aluno == null)
			return;

		StringBuilder sb = new StringBuilder();
		sb.append("\n------------------------------------\n").append(aluno.toString(true)).append("Cursos: ");
		for (Curso c : aluno.getCursos()) {
			sb.append(c.getCodigo()).append(" ");
		}
		sb.append("\n------------------------------------\n");

		UIUtils.createWindow("Consulta Aluno", sb.toString());
	}

	// remove o aluno de todos os cursos matriculados
	private static void limparCursos(Escola e) {
		Aluno aluno = Aluno.selecionarAluno(e);
		if (aluno == null) {
			return;
		}
		for (Curso c : aluno.cursos) {
			c.getAlunos().remove(aluno);
		}
		aluno.cursos.clear();

		// redistribui as salas
		e.distribuirSalas();
		JOptionPane.showMessageDialog(null, "Cursos removidos.", "Matrícula", JOptionPane.INFORMATION_MESSAGE);
	}

	// matricula o aluno em um curso
	private static void matricularEmCurso(Escola e) {
		Aluno aluno = Aluno.selecionarAluno(e);
		if (aluno == null) {
			return;
		}
		Curso curso = Curso.selecionarCurso(e);
		if (curso == null) {
			return;
		}
		if (curso.getAlunos().contains(aluno)) {
			JOptionPane.showMessageDialog(null, "O aluno já encontra-se matriculado no curso.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		curso.getAlunos().add(aluno);
		aluno.cursos.add(curso);
		if (!e.distribuirSalas()) {
			curso.getAlunos().remove(curso.getAlunos().size() - 1);
			aluno.cursos.remove(aluno.cursos.size() - 1);
			JOptionPane.showMessageDialog(null, "Não foi possível matricular o aluno. A sala já está lotada.", "Erro",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,
					"Aluno " + aluno.getNome() + " matriculado com sucesso na disciplina " + curso.getCodigo() + ".",
					"Matrícula", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	// altera os dados cadastrais do aluno
	private static void alterarDadosPessoais(Escola e) {
		Aluno selecionado = selecionarAluno(e);
		if (selecionado == null) {
			return;
		}

		Pessoa novo = (new TelaCadastroPessoa()).getPessoa();
		if (novo == null)
			return;

		selecionado.update(novo);
		JOptionPane.showMessageDialog(null, "Aluno atualizado.", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
	}

	// cria uma dialog para selecionar um aluno
	public static Aluno selecionarAluno(Escola e) {
		final Aluno selecionado = new Aluno();

		JDialog dialog = UIUtils.createEmptyDialog("Selecionar aluno");
		JLabel alunoLabel = new JLabel("Selecione o aluno: ");
		JComboBox<Aluno> alunoBox = new JComboBox<Aluno>();
		for (Aluno aluno : e.getAlunos()) {
			alunoBox.addItem(aluno);
		}
		JButton cadastrarButton = new JButton("Selecionar");
		cadastrarButton.addActionListener(event -> {
			selecionado.update((Aluno) alunoBox.getSelectedItem());
			dialog.dispose();
		});
		UIUtils.addComponent(dialog, alunoLabel, 0, 0);
		UIUtils.addComponent(dialog, alunoBox, 1, 0);
		UIUtils.addComponent(dialog, cadastrarButton, 1, 1);
		UIUtils.showDialog(dialog);
		if (selecionado.matricula != null) {
			return e.getAlunos().stream().filter(a -> a.getMatricula().equals(selecionado.getMatricula())).findAny()
					.get();
		}
		return null;
	}

	public void update(Aluno a) {
		super.update(a);
		matricula = a.matricula;
		cursos = a.cursos;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public String toString(boolean gestor) {
		return "Matrícula: " + matricula + "\n" + super.toString(gestor);
	}

	public String toString() {
		return matricula + " - " + getNome();
	}

}
