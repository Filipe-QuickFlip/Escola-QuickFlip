package br.com.escola.turma;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record TurmaDadosAdicaoDisciplinas(
		@NotNull
		Long idTurma,
		@NotNull
		List<Long> idDisciplinas) {

}
