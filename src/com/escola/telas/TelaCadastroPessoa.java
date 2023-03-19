package com.escola.telas;

import java.awt.Dialog;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.escola.classes.Pessoa;
import com.escola.util.UIUtils;

public class TelaCadastroPessoa extends JDialog {
	private static final long serialVersionUID = -5661648333667284600L;

	private Pessoa pessoa;

	public TelaCadastroPessoa() {
		super(null, "Cadastro de Pessoa", Dialog.ModalityType.APPLICATION_MODAL);

		JLabel cpfLabel = new JLabel("CPF:");
		JTextField cpfTextField = new JTextField();
		JLabel nomeLabel = new JLabel("Nome:");
		JTextField nomeTextField = new JTextField();
		JLabel telLabel = new JLabel("Telefone:");
		JTextField telTextField = new JTextField();

		JButton cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.addActionListener(event -> {
			String cpf = cpfTextField.getText();
			String nome = nomeTextField.getText();
			String tel = telTextField.getText();

			pessoa = new Pessoa(cpf, nome, tel);

			this.dispose();
		});

		GridBagLayout layout = new GridBagLayout();
		getContentPane().setLayout(layout);
		UIUtils.addComponent(getContentPane(), cpfLabel, 0, 0);
		UIUtils.addComponent(getContentPane(), cpfTextField, 1, 0);
		UIUtils.addComponent(getContentPane(), nomeLabel, 0, 1);
		UIUtils.addComponent(getContentPane(), nomeTextField, 1, 1);
		UIUtils.addComponent(getContentPane(), telLabel, 0, 2);
		UIUtils.addComponent(getContentPane(), telTextField, 1, 2);
		UIUtils.addComponent(getContentPane(), cadastrarButton, 1, 3);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
}
