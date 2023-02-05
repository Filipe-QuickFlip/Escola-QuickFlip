package br.com.escola.disciplina;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DisciplinaDadosCadastro(
		@NotBlank
		String nome,
		@NotNull
		BigDecimal cargaHoraria) {
}