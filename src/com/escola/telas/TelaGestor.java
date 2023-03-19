package com.escola.telas;

import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.escola.enums.TipoItem;
import com.escola.util.UIUtils;

public class TelaGestor extends JDialog {

	private static final long serialVersionUID = -2960906858204910588L;

	private TipoItem selectedButton;

	public TelaGestor() {
		super(null, "Menu do Gestor", Dialog.ModalityType.APPLICATION_MODAL);

		GridBagLayout layout = new GridBagLayout();
		getContentPane().setLayout(layout);

		int tam = 0;
		for (TipoItem item : TipoItem.values()) {
			JButton botao = new JButton("Gerir " + item.getValue());
			botao.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectedButton = item;
					dispose();
				}
			});
			UIUtils.addComponent(this.getContentPane(), botao, tam++, 0);
		}

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public TipoItem getSelectedButton() {
		return selectedButton;
	}

}