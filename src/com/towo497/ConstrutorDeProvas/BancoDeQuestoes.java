package com.towo497.ConstrutorDeProvas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class BancoDeQuestoes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private File bancoDeQuestoes;
	private ArrayList<Questao> listaQuestoes = new ArrayList<Questao>();
		
	
	protected void setListaQuestoes(ArrayList<Questao> listaQuestoes) {
		this.listaQuestoes = listaQuestoes;
	}	
	
	public void adiciarQuestaoEm(int numQuestao, Questao questao) {
		listaQuestoes.add(numQuestao - 1, questao);
	}
	
	public void adiciarQuestao(Questao questao) {
		listaQuestoes.add(questao);
	}
	
	public void removerQuestaoEm(int numQuestao) {
		listaQuestoes.remove(numQuestao - 1);
	}
	
	public void salvar() {	
		JFileChooser save = new JFileChooser();
		save.showSaveDialog(save);
		this.bancoDeQuestoes = save.getSelectedFile();
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(bancoDeQuestoes));
			os.writeObject(this);		
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	protected Questao getQuestao(int numQuestao) {
		return listaQuestoes.get(numQuestao - 1);
	}
	
	protected int tamanho() {
		return listaQuestoes.size();
	}
	
}
