package com.escola.main;

import static com.escola.enums.TipoItem.ALUNO;
import static com.escola.enums.TipoItem.CURSO;
import static com.escola.enums.TipoItem.PROFESSOR;
import static com.escola.enums.TipoItem.SALA;

import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.escola.classes.Escola;
import com.escola.enums.TipoItem;
import com.escola.enums.TipoPerfil;
import com.escola.telas.TelaGestor;
import com.escola.telas.TelaPerfis;
import com.escola.telas.TelaRelatorios;

public class Main {

	public static void main(String[] args) {
		Escola e = Escola.criarEscolaPadrao();

		// loop para o menu não fechar
		while (true) {
			// exibe dialog para selecionar perfil
			TipoPerfil perfil = obterPerfil();
			switch (perfil) {
			case ALUNO:
				exibirRelatorios(e, Arrays.asList(PROFESSOR, CURSO, SALA));
				break;
			case PROFESSOR:
				exibirRelatorios(e, Arrays.asList(ALUNO, PROFESSOR, CURSO, SALA));
				break;
			case GESTOR:
				acessoGestor(e);
				break;
			}
		}
	}

	// mostra o menu de gerência da escola
	public static void menuGestor(Escola e) {
		while (true) {
			// abre a dialog de selecionar a ação do gestor
			TelaGestor telaGestor = new TelaGestor();
			// obtem o tipo de item
			TipoItem item = telaGestor.getSelectedButton();
			// caso clicado em sair, fechar a aplicação
			if (item == null) {
				return;
			}
			e.gerenciar(item);
		}
	}

	// mostra a tela de login para os gestores
	public static boolean loginGestor(Escola e) {
		JTextField usuarioField = new JTextField(10);
		JPasswordField senhaField = new JPasswordField(10);
		Object[] message = { "Insira o usuario do gestor:", usuarioField, "Insira a senha do gestor:", senhaField };
		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String usuario = usuarioField.getText();
			String senha = new String(senhaField.getPassword());
			return e.validarLoginGestor(usuario, senha);
		}
		return false;
	}

	// valida o login do gestor antes de abrir o menu de gestor
	private static void acessoGestor(Escola e) {
		if (!loginGestor(e)) {
			JOptionPane.showMessageDialog(null, "Usuário não encontrado.", "Alert", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Login realizado com sucesso.", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			menuGestor(e);
		}
	}

	// mostra os relatórios especificados na variavel relatorios
	private static void exibirRelatorios(Escola escola, List<TipoItem> relatorios) {
		while (true) {
			TipoItem relatorio = exibirPromptRelatorios(relatorios);
			// caso clicado em sair, sair do loop
			if (relatorio == null)
				break;
			escola.exibirRelatorio(relatorio, false);
		}
	}

	// exibe uma dialog de tipo de relatórios
	private static TipoPerfil obterPerfil() {
		// abre a dialog de selecionar o perfil
		TelaPerfis telaPerfis = new TelaPerfis();
		// obtem o tipo de perfil
		TipoPerfil perfil = telaPerfis.getSelectedButton();
		// caso clicado em sair, fechar a aplicação
		if (perfil == null) {
			System.exit(0);
		}
		return perfil;
	}

	// exibe uma dialog de tipo de relatorios
	private static TipoItem exibirPromptRelatorios(List<TipoItem> relatorios) {
		TelaRelatorios telaRelatorios = new TelaRelatorios(relatorios);
		return telaRelatorios.getSelectedButton();
	}

}
