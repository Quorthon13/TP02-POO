package com.escola.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UIUtils {
	public static void createWindow(String title, String text) {
		JDialog dialog = new JDialog();
		JTextArea textArea = new JTextArea(text);
		JScrollPane scrollPane = new JScrollPane(textArea);

		dialog.add(scrollPane);
		dialog.setTitle(title);
		dialog.setSize(400, 300);
		dialog.setLocationRelativeTo(null); // center the dialog on screen
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModal(true); // make the dialog modal
		dialog.setVisible(true);
	}

	public static void addComponent(Container contentPane, Component component, int column, int row, int anchor,
			int fill, int ipadx, int ipady) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.anchor = anchor;
		constraints.fill = fill;
		constraints.ipadx = ipadx;
		constraints.ipady = ipady;
		constraints.insets = new Insets(5, 5, 5, 5);
		((GridBagLayout) contentPane.getLayout()).setConstraints(component, constraints);
		contentPane.add(component);
	}

	public static void addComponent(JDialog dialog, Component component, int column, int row) {
		addComponent(dialog.getContentPane(), component, //
				column, row, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0);
	}

	public static void addComponent(Container contentPane, Component component, int column, int row) {
		addComponent(contentPane, component, //
				column, row, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0);
	}

	public static JDialog createEmptyDialog(String title) {
		JDialog dialog = new JDialog(null, title, Dialog.ModalityType.APPLICATION_MODAL);
		GridBagLayout layout = new GridBagLayout();
		dialog.getContentPane().setLayout(layout);

		return dialog;
	}

	public static void showDialog(JDialog dialog) {
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
