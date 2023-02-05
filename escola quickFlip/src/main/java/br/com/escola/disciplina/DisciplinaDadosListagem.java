package br.com.escola.disciplina;

import java.math.BigDecimal;

public record DisciplinaDadosListagem(
		Long id,
		String nome,
		BigDecimal cargaHoraria
		) {
	
	public DisciplinaDadosListagem(Disciplina disciplina) {
		this(disciplina.getId(), disciplina.getNome(), disciplina.getCargaHoraria());
	}
}