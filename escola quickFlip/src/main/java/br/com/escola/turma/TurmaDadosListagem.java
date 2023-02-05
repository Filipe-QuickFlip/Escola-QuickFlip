package br.com.escola.turma;

public record TurmaDadosListagem(
		Long id,
		String codigo,
		Periodo periodo, 
		Serie serie) {
	
	public TurmaDadosListagem(Turma turma) {
		this(turma.getId(), turma.getCodigo(), turma.getPeriodo(), turma.getSerie());
	}


}
