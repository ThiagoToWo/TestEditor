package com.towo497.ConstrutorDeProvas;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
		//ProcessBuilder pbtxt = new ProcessBuilder("notepad.exe", aProva.getSelectedFile().getName());	
		//ProcessBuilder pb = new ProcessBuilder("cmd","/c", "pdflatex", aProva.getSelectedFile().getName() + ".tex");
		try {
			//Process ptxt = pbtxt.start();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(aProva.getSelectedFile()), "UTF-8"));
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
			//ptxt.waitFor();
			//pb.start();
		} catch (IOException /*| InterruptedException*/ e) {
			e.printStackTrace();
		}		
	}
}
