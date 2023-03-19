package com.escola.telas;

import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.escola.enums.TipoItem;
import com.escola.util.UIUtils;

public class TelaUpdate extends JDialog {
	private static final long serialVersionUID = 6614267880326954219L;

	private Integer selectedButton;

	public TelaUpdate(TipoItem item, List<String> opcoes) {
		super(null, "Atualizar " + item.getValue(), Dialog.ModalityType.APPLICATION_MODAL);

		GridBagLayout layout = new GridBagLayout();

		getContentPane().setLayout(layout);
		int tam = 0;
		for (String op : opcoes) {
			JButton btn = new JButton(op);
			final int tamFinal = tam;
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectedButton = tamFinal;
					dispose();
				}
			});
			UIUtils.addComponent(this.getContentPane(), btn, tam++, 0);
		}

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public Integer getSelectedButton() {
		return selectedButton;
	}

	public void setSelectedButton(int selectedButton) {
		this.selectedButton = selectedButton;
	}

}
