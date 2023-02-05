package br.com.escola.turma;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TurmaDadosAtualizacao(
		@NotNull
		Long id,
		@Pattern(regexp = "\\w{2}")
		String codigo,
		Periodo periodo,
		Serie serie) {

}
