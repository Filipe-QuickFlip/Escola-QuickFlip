package br.com.escola.aula;

public enum Ordem {
	
	PRIMEIRA_AULA("primeira aula"),
	SEGUNDA_AULA("segunda aula"),
	TERCEIRA_AULA("terceira aula"),
	QUARTA_AULA("quarta aula"),
	QUINTA_AULA("quinta aula");
	
	private String mensagem;
	
	Ordem(String mensagem){
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
