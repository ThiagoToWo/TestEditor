package com.towo497.ConstrutorDeProvas;

import java.io.Serializable;

public class Questao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String pergunta;
	private String resposta;
	
	public Questao(String pergunta, String resposta) {
		this.pergunta = pergunta;
		this.resposta = resposta;
	}

	public String getPergunta() {
		return pergunta;
	}

	public String getResposta() {
		return resposta;
	}
	
	public void setSelected() {
		this.pergunta += " SELECIONADA";
	}

	@Override
	public String toString() {
		return pergunta + "\nResposta=" + resposta;
	}
	
	
}
