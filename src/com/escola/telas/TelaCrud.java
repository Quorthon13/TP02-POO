package com.escola.telas;

import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.escola.enums.OperacaoEnum;
import com.escola.enums.TipoItem;
import com.escola.util.UIUtils;

public class TelaCrud extends JDialog {

	private static final long serialVersionUID = -2960906858204910588L;

	private OperacaoEnum selectedButton;

	public TelaCrud(TipoItem item) {
		super(null, "Gerenciar " + item.getValue(), Dialog.ModalityType.APPLICATION_MODAL);

		GridBagLayout layout = new GridBagLayout();
		getContentPane().setLayout(layout);

		int i = 0;
		for (OperacaoEnum operacao : OperacaoEnum.values()) {
			addButton(operacao.getValue(), operacao, i++, 0);
		}

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addButton(String label, OperacaoEnum tipoPerfil, int column, int row) {
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

	public OperacaoEnum getSelectedButton() {
		return selectedButton;
	}

}