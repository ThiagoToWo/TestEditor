package com.towo497.ConstrutorDeProvas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class EditorDeProvas {
	
	private ArrayList<Questao> prova = new ArrayList<Questao>();
		
	protected void setProva(ArrayList<Questao> prova) {
		this.prova = prova;
	}
		
	public void construirProva() {
		JFileChooser aProva = new JFileChooser();
		aProva.showSaveDialog(aProva);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(aProva.getSelectedFile()));
			for (int i = 0; i < prova.size(); i++) {
				bw.write((i + 1) + ". ");
				bw.write(prova.get(i).getPergunta());
				bw.write("\n\n");
			}
			bw.write("\n");
			bw.write("GABARITO");
			bw.write("\n\n");
			for (int i = 0; i < prova.size(); i++) {
				bw.write((i + 1) + ". ");
				bw.write(prova.get(i).getResposta());
				bw.write("\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
