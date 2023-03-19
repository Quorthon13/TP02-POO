package com.escola.telas;

import java.awt.Dialog;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.escola.classes.Aluno;
import com.escola.classes.Escola;
import com.escola.classes.Pessoa;
import com.escola.util.UIUtils;

public class TelaCadastroAluno extends JDialog {
	private static final long serialVersionUID = -5661648333667284600L;

	private Aluno aluno;

	public TelaCadastroAluno(Escola escola) {
		super(null, "Cadastro de Aluno", Dialog.ModalityType.APPLICATION_MODAL);
		Pessoa p = new TelaCadastroPessoa().getPessoa();
		if (p == null) {
			dispose();
			return;
		}

		JLabel matriculaLabel = new JLabel("Matricula:");
		JTextField matriculaTextField = new JTextField();

		JButton cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.addActionListener(event -> {
			String matricula = matriculaTextField.getText();

			aluno = new Aluno(p.getCpf(), p.getNome(), p.getTel(), matricula);

			this.dispose();
		});

		GridBagLayout layout = new GridBagLayout();
		getContentPane().setLayout(layout);
		UIUtils.addComponent(getContentPane(), matriculaLabel, 0, 0);
		UIUtils.addComponent(getContentPane(), matriculaTextField, 1, 0);
		UIUtils.addComponent(getContentPane(), cadastrarButton, 1, 1);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}
