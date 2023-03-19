package com.escola.telas;

import java.awt.Dialog;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.escola.classes.Sala;
import com.escola.util.UIUtils;

public class TelaCadastroSala extends JDialog {
	private static final long serialVersionUID = 8106126422319759660L;

	private Sala sala;

	public TelaCadastroSala() {
		super(null, "Cadastro de Sala", Dialog.ModalityType.APPLICATION_MODAL);

		JLabel capacidadeLabel = new JLabel("Capacidade:");
		JTextField capacidadeTextField = new JTextField();

		JButton cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.addActionListener(event -> {
			int capacidade = Integer.parseInt(capacidadeTextField.getText());

			sala = new Sala();
			sala.setCapacidade(capacidade);

			this.dispose();
		});

		GridBagLayout layout = new GridBagLayout();
		getContentPane().setLayout(layout);
		UIUtils.addComponent(getContentPane(), capacidadeLabel, 0, 0);
		UIUtils.addComponent(getContentPane(), capacidadeTextField, 1, 0);
		UIUtils.addComponent(getContentPane(), cadastrarButton, 1, 1);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

}
