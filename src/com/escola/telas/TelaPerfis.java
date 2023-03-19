package com.escola.telas;

import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.escola.enums.TipoPerfil;
import com.escola.util.UIUtils;

public class TelaPerfis extends JDialog {

	private static final long serialVersionUID = -2960906858204910588L;

	private TipoPerfil selectedButton;

	public TelaPerfis() {
		super(null, "Selecione o perfil", Dialog.ModalityType.APPLICATION_MODAL);

		GridBagLayout layout = new GridBagLayout();
		getContentPane().setLayout(layout);

		addButton("Aluno", TipoPerfil.ALUNO, 0, 0);
		addButton("Professor", TipoPerfil.PROFESSOR, 1, 0);
		addButton("Gestor", TipoPerfil.GESTOR, 2, 0);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addButton(String label, TipoPerfil tipoPerfil, int column, int row) {
		JButton button = new JButton(label);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedButton = tipoPerfil;
				dispose();
			}
		});
		UIUtils.addComponent(this.getContentPane(), button, column, row);
	}

	public TipoPerfil getSelectedButton() {
		return selectedButton;
	}

}