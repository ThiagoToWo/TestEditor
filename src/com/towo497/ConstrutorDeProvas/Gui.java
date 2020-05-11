package com.towo497.ConstrutorDeProvas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Gui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private String autor = "Autor: Thiago de Oliveira Alves\ntowo497@gmail.com";
	private String versao = "Versão: 0.0 \n 07-05-2020\n\n";
	JLabel rotPergunta;
	private JTextArea txtPergunta = new JTextArea(15, 3);
	private JTextArea txtResposta = new JTextArea(5, 3);
	private static ArrayList<Questao> listaProvisoria = new ArrayList<Questao>();
	private static ArrayList<Questao> listaProvisoriaBanco = new ArrayList<Questao>();
	private BancoDeQuestoes banco = new BancoDeQuestoes();
	private EditorDeProvas editor = new EditorDeProvas();
	int numeroQuestao = 1;
	
	public void construir() {
		setTitle("Construtor de provas");
		Font bigFont = new Font ("sanserif", Font.BOLD, 24);
		
		// barra de menu
		JMenuBar barraDeMenu = new JMenuBar();		
		JMenu menuArquivos = new JMenu("Arquivos");	
		menuArquivos.setFont(bigFont);
		JMenuItem abrirBanco = new JMenuItem("Abrir Arquivo de Dados");
		abrirBanco.setFont(bigFont);
		abrirBanco.addActionListener(new AbrirListener());
		JMenuItem criarNovo = new JMenuItem("Criar Novo Arquivo de Dados");
		criarNovo.setFont(bigFont);
		criarNovo.addActionListener(new NovoListener());
		JMenuItem salvarBanco = new JMenuItem("Salvar como Arquivo de Dados");
		salvarBanco.setFont(bigFont);
		salvarBanco.addActionListener(new SalvarBancoListener());
		JMenuItem salvarProva = new JMenuItem("Salvar como Arquivo de Texto");
		salvarProva.setFont(bigFont);
		salvarProva.addActionListener(new SalvarProvaListener());
		JMenu menuSobre = new JMenu("Informações");
		menuSobre.setFont(bigFont);
		JMenuItem autoria = new JMenuItem("Autor");
		autoria.setFont(bigFont);
		autoria.addActionListener(new AutorListener());
		JMenuItem versao = new JMenuItem("Sobre o aplicativo");
		versao.setFont(bigFont);
		versao.addActionListener(new VersaoListener());
		menuArquivos.add(abrirBanco);
		menuArquivos.add(criarNovo);
		menuArquivos.add(salvarBanco);
		menuArquivos.add(salvarProva);		
		menuSobre.add(autoria);
		menuSobre.add(versao);
		barraDeMenu.add(menuArquivos);
		barraDeMenu.add(menuSobre);
		setJMenuBar(barraDeMenu);
		
		// painel norte
		JPanel painelNorte = new JPanel();
		painelNorte.setBorder(BorderFactory.createRaisedBevelBorder());
		JButton botDeletar = new JButton("Deletar Questão");
		botDeletar.setFont(bigFont);
		botDeletar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {			
				banco.removerQuestaoEm(numeroQuestao);
				listaProvisoriaBanco.removeAll(listaProvisoriaBanco);
				for (int i = 0; i < banco.tamanho(); i++) {
					listaProvisoriaBanco.add(banco.getQuestao(i + 1));
				}
				txtPergunta.setText("");
				txtResposta.setText("");
			}
		});	
		JButton botCriarNova = new JButton("Criar Nova Questão");
		botCriarNova.setFont(bigFont);
		botCriarNova.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtResposta.isEnabled() || !txtPergunta.isEnabled()) {					
					Questao novaQuestao = new Questao("Insira uma questão e substitua a edição.",
							"Insira uma resposta e substitua a edição.");	
					banco.adiciarQuestao(novaQuestao);
					txtPergunta.setEnabled(true);
					txtResposta.setEnabled(true);
					txtPergunta.setText(banco.getQuestao(numeroQuestao).getPergunta());
					txtResposta.setText(banco.getQuestao(numeroQuestao).getResposta());
					rotPergunta.setText("Questão " + numeroQuestao + ".");
					listaProvisoriaBanco.removeAll(listaProvisoriaBanco);
					for (int i = 0; i < banco.tamanho(); i++) {
						listaProvisoriaBanco.add(banco.getQuestao(i + 1));
					}
				} else {
					Questao novaQuestao = new Questao("Insira uma questão e substitua a edição.",
							"Insira uma resposta e substitua a edição.");	
					banco.adiciarQuestao(novaQuestao);
					listaProvisoriaBanco.removeAll(listaProvisoriaBanco);
					for (int i = 0; i < banco.tamanho(); i++) {
						listaProvisoriaBanco.add(banco.getQuestao(i + 1));
					}
				}				
			}
		});	
		JButton botSubstituir = new JButton("Substituir Edição");
		botSubstituir.setFont(bigFont);
		botSubstituir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Questao novaQuestao = new Questao(txtPergunta.getText(), txtResposta.getText());				
				banco.adiciarQuestaoEm(numeroQuestao, novaQuestao);
				banco.removerQuestaoEm(numeroQuestao + 1);
				listaProvisoriaBanco.removeAll(listaProvisoriaBanco);
				for (int i = 0; i < banco.tamanho(); i++) {
					listaProvisoriaBanco.add(banco.getQuestao(i + 1));
				}
			}
		});	
		JLabel rotAdmin = new JLabel("Administrar Arquivo de Dados");
		rotAdmin.setFont(bigFont);
		painelNorte.add(rotAdmin);
		painelNorte.add(botCriarNova);
		painelNorte.add(botDeletar);
		painelNorte.add(botSubstituir);
		getContentPane().add(BorderLayout.NORTH, painelNorte);
		
		// painel central
		JPanel painelCentral = new JPanel();
		painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
		painelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		rotPergunta = new JLabel("Questão.");
		rotPergunta.setFont(bigFont);
		JLabel rotResposta = new JLabel("Resposta.");
		rotResposta.setFont(bigFont);
		txtPergunta.setLineWrap(true);		
		txtPergunta.setFont(bigFont);
		txtPergunta.setEnabled(false);
		txtResposta.setLineWrap(true);
		txtResposta.setFont(bigFont);
		txtResposta.setEnabled(false);
		JScrollPane rolagemPergunta = new JScrollPane(txtPergunta);
		JScrollPane rolagemResposta = new JScrollPane(txtResposta);
		painelCentral.add(rotPergunta);
		painelCentral.add(rolagemPergunta);
		painelCentral.add(rotResposta);
		painelCentral.add(rolagemResposta);
		getContentPane().add(BorderLayout.CENTER, painelCentral);
		
		// painel sul
		JPanel painelSul = new JPanel();
		painelSul.setBorder(BorderFactory.createRaisedBevelBorder());
		JButton botCopiarTudo = new JButton("Copiar Todas");
		botCopiarTudo.setFont(bigFont);
		botCopiarTudo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 1; i <= banco.tamanho(); i++) {
					listaProvisoria.add(banco.getQuestao(i));
				}
			}
		});			
		JButton botSelecionar = new JButton("Selecionar Questão");
		botSelecionar.setFont(bigFont);
		botSelecionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Questao novaQuestao = new Questao(txtPergunta.getText(), txtResposta.getText());
				listaProvisoria.add(novaQuestao);
				banco.getQuestao(numeroQuestao).setSelected();
			}
		});			
		JButton botaoUltimo = new JButton(">>|");
		botaoUltimo.setFont(bigFont);
		botaoUltimo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				numeroQuestao = banco.tamanho();				
				txtPergunta.setText(banco.getQuestao(numeroQuestao).getPergunta());
				txtResposta.setText(banco.getQuestao(numeroQuestao).getResposta());
				txtPergunta.moveCaretPosition(0);
				txtResposta.moveCaretPosition(0);
				rotPergunta.setText("Questão " + numeroQuestao + ".");
			}
		});
		JButton botaoAvançar = new JButton(">>");
		botaoAvançar.setFont(bigFont);
		botaoAvançar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (numeroQuestao < banco.tamanho()) {
					numeroQuestao++;
					txtPergunta.setText(banco.getQuestao(numeroQuestao).getPergunta());
					txtResposta.setText(banco.getQuestao(numeroQuestao).getResposta());
					txtPergunta.moveCaretPosition(0);
					txtResposta.moveCaretPosition(0);
					rotPergunta.setText("Questão " + numeroQuestao + ".");
				} else {					
					numeroQuestao = banco.tamanho();					
					rotPergunta.setText("Questão " + numeroQuestao + ".");
				}
			}
		});
		JButton botaoRecuar = new JButton("<<");
		botaoRecuar.setFont(bigFont);
		botaoRecuar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (numeroQuestao > 1) {
					numeroQuestao--;
					txtPergunta.setText(banco.getQuestao(numeroQuestao).getPergunta());
					txtResposta.setText(banco.getQuestao(numeroQuestao).getResposta());
					txtPergunta.moveCaretPosition(0);
					txtResposta.moveCaretPosition(0);
					rotPergunta.setText("Questão " + numeroQuestao + ".");
				}  else {					
					numeroQuestao = 1;					
					rotPergunta.setText("Questão " + numeroQuestao + ".");
				}
			}
		});
		JButton botaoPrimeiro = new JButton("|<<");
		botaoPrimeiro.setFont(bigFont);
		botaoPrimeiro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				numeroQuestao = 1;
				txtPergunta.setText(banco.getQuestao(numeroQuestao).getPergunta());
				txtResposta.setText(banco.getQuestao(numeroQuestao).getResposta());
				txtPergunta.moveCaretPosition(0);
				txtResposta.moveCaretPosition(0);
				rotPergunta.setText("Questão " + numeroQuestao + ".");
			}
		});
		painelSul.add(botaoPrimeiro);
		painelSul.add(botaoRecuar);
		painelSul.add(botaoAvançar);
		painelSul.add(botaoUltimo);
		painelSul.add(botSelecionar);
		painelSul.add(botCopiarTudo);
		getContentPane().add(BorderLayout.SOUTH, painelSul);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(350, 100);
		pack();
		setVisible(true);
	}
	
	public class NovoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Gui().construir();
		}

	}
	
	public class SalvarProvaListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.setProva(listaProvisoria);
			editor.construirProva();

		}

	}

	public class SalvarBancoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			BancoDeQuestoes novoBanco = new BancoDeQuestoes();				
			novoBanco.setListaQuestoes(listaProvisoriaBanco);
			novoBanco.salvar();

		}

	}

	public class AbrirListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			numeroQuestao = 1;			
			JFileChooser arquivo = new JFileChooser();
			arquivo.showOpenDialog(arquivo);
			try {
				ObjectInputStream is = new ObjectInputStream(new FileInputStream(arquivo.getSelectedFile()));
				banco = (BancoDeQuestoes) is.readObject();
				txtPergunta.setEnabled(true);
				txtResposta.setEnabled(true);
				txtPergunta.setText(banco.getQuestao(1).getPergunta());
				txtResposta.setText(banco.getQuestao(1).getResposta());
				rotPergunta.setText("Questão " + numeroQuestao + ".");
				is.close();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (StreamCorruptedException e1) {
				JOptionPane.showMessageDialog(getParent(), "Formato de arquivo inválido", "Erro", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {			
				e1.printStackTrace();
			}

		}

	}
	
	private class AutorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {			
			JOptionPane.showMessageDialog(null, autor, "Sobre mim", JOptionPane.INFORMATION_MESSAGE);

		}

	}
	
	private class VersaoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, versao, "Sobre este", JOptionPane.INFORMATION_MESSAGE);

		}

	}	
}
