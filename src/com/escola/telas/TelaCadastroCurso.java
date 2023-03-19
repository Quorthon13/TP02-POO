package com.escola.telas;

import java.awt.Dialog;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.escola.classes.Curso;
import com.escola.classes.Escola;
import com.escola.classes.Professor;
import com.escola.util.UIUtils;

public class TelaCadastroCurso extends JDialog {
	private static final long serialVersionUID = 8368210469132042743L;

	private Curso curso;

	public TelaCadastroCurso(Escola escola) {
		super(null, "Cadastro de Curso", Dialog.ModalityType.APPLICATION_MODAL);

		JLabel codeLabel = new JLabel("Insira o codigo do curso: ");
		JTextField codeField = new JTextField();

		JLabel nameLabel = new JLabel("Insira o nome do curso: ");
		JTextField nameField = new JTextField();

		JLabel professorLabel = new JLabel("Selecione o professor desse curso: ");
		JComboBox<Professor> professorBox = new JComboBox<>();
		for (Professor professor : escola.getProfessores()) {
			professorBox.addItem(professor);
		}
		JButton cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.addActionListener(event -> {
			String codigo = codeField.getText();
			String nome = nameField.getText();
			Professor professor = (Professor) professorBox.getSelectedItem();

			curso = new Curso(codigo, nome, professor);
			this.dispose();
		});

		GridBagLayout layout = new GridBagLayout();
		getContentPane().setLayout(layout);
		UIUtils.addComponent(getContentPane(), codeLabel, 0, 0);
		UIUtils.addComponent(getContentPane(), codeField, 1, 0);
		UIUtils.addComponent(getContentPane(), nameLabel, 0, 1);
		UIUtils.addComponent(getContentPane(), nameField, 1, 1);
		UIUtils.addComponent(getContentPane(), professorLabel, 0, 2);
		UIUtils.addComponent(getContentPane(), professorBox, 1, 2);
		UIUtils.addComponent(getContentPane(), cadastrarButton, 1, 3);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}
