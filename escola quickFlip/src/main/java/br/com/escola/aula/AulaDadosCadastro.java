package br.com.escola.aula;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public record AulaDadosCadastro(
		@NotNull
		@JsonFormat(pattern = "dd/MM/yyyy")
		LocalDate dia,
		@NotNull
		Ordem ordem,
		@NotNull
		Long idDisciplina,
		@NotNull
		Long idProfessor,
		@NotNull
		Long idTurma
		) {

}
