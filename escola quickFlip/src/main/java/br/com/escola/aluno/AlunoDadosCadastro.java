package br.com.escola.aluno;

import br.com.escola.endereco.EnderecoDadosCadastro;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AlunoDadosCadastro(
		@NotBlank
		String nome,
		@NotBlank
		@Pattern(regexp = "\\d{6}")
		String matricula,
		@NotBlank
		@Pattern(regexp = "\\d{11}")
		String cpf,
		@NotBlank
		@Email
		String email,
		@NotBlank
		@Pattern(regexp = "\\d{10,11}")
		String telefone,
		@NotNull
		@Valid
		EnderecoDadosCadastro endereco
		) {
	
}

