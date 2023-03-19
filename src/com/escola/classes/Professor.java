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
import com.escola.telas.TelaCadastroPessoa;
import com.escola.telas.TelaUpdate;
import com.escola.util.UIUtils;

public class Professor extends Pessoa implements Crud {
	private List<Curso> cursos;

	public Professor() {
		this.cursos = new ArrayList<Curso>();
	}

	public Professor(String cpf, String nome, String tel) {
		super(cpf, nome, tel);
		this.cursos = new ArrayList<Curso>();
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public static void cadastrar(Escola e) {
		Pessoa pessoa = new TelaCadastroPessoa().getPessoa();
		if (pessoa != null) {
			Professor prof = new Professor(pessoa.getCpf(), pessoa.getNome(), pessoa.getTel());
			e.getProfessores().add(prof);
			JOptionPane.showMessageDialog(null, "Professor " + pessoa.getNome() + " cadastrado.", "Cadastro",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void atualizar(Escola e) {
		Integer opcao = new TelaUpdate(TipoItem.PROFESSOR, Arrays.asList("Alterar dados pessoais")).getSelectedButton();

		if (opcao == null) {
			return;
		}

		switch (opcao) {
		case 0:
			alterarDadosPessoais(e);
			break;
		}
	}

	private static void alterarDadosPessoais(Escola e) {
		Professor selecionado = selecionarProfessor(e);
		if (selecionado == null) {
			return;
		}

		Pessoa novo = (new TelaCadastroPessoa()).getPessoa();
		if (novo == null)
			return;

		selecionado.update(novo);
		JOptionPane.showMessageDialog(null, "Professor atualizado.", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
	}

	// cria uma dialog para selecionar um professor
	public static Professor selecionarProfessor(Escola e) {
		final Professor selecionado = new Professor();

		JDialog dialog = UIUtils.createEmptyDialog("Selecionar professor");
		JLabel professorLabel = new JLabel("Selecione o professor: ");
		JComboBox<Professor> professorBox = new JComboBox<Professor>();
		for (Professor professor : e.getProfessores()) {
			professorBox.addItem(professor);
		}
		JButton cadastrarButton = new JButton("Selecionar");
		cadastrarButton.addActionListener(event -> {
			selecionado.update((Professor) professorBox.getSelectedItem());
			dialog.dispose();
		});
		UIUtils.addComponent(dialog, professorLabel, 0, 0);
		UIUtils.addComponent(dialog, professorBox, 1, 0);
		UIUtils.addComponent(dialog, cadastrarButton, 1, 1);
		UIUtils.showDialog(dialog);
		if (selecionado.getCpf() != null) {
			return e.getProfessores().stream().filter(a -> a.getCpf().equals(selecionado.getCpf())).findAny().get();
		}
		return null;
	}

	// remove o professor
	public static void excluir(Escola e) {
		Professor prof = selecionarProfessor(e);
		if (prof == null)
			return;

		for (Curso c : prof.cursos) {
			c.setProfessor(null);
		}

		e.getProfessores().removeIf(p -> p == prof);

		JOptionPane.showMessageDialog(null, "Professor " + prof + " removido.", "Excluir",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void consultar(Escola e) {
		Professor p = selecionarProfessor(e);
		if (p == null)
			return;

		StringBuilder sb = new StringBuilder();
		sb.append("\n------------------------------------\n").append(p.toString(true)).append("\nCursos: ");
		for (Curso c : p.getCursos()) {
			sb.append(c.getCodigo()).append(" ");
		}
		sb.append("\n------------------------------------\n");

		UIUtils.createWindow("Consulta Professor", sb.toString());

	}

	public String toString() {
		return getNome();
	}
}
