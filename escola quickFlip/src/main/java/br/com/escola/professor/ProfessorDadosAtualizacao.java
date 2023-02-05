package br.com.escola.professor;

import java.math.BigDecimal;

import br.com.escola.endereco.EnderecoDadosAtualizacao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProfessorDadosAtualizacao(
		@NotNull
		Long id,
		String nome,
		@Email
		String email,
		@Pattern(regexp = "\\d{10,11}")
		String telefone,
		BigDecimal salario,
		String formacao,
		@Valid
		EnderecoDadosAtualizacao endereco
		) {


}
