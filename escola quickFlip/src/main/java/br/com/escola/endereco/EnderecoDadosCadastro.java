package br.com.escola.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnderecoDadosCadastro(
		@NotBlank
		String logradouro,
		@NotNull
		Integer numero,
		String complemento,
		@NotBlank
		String bairro,
		@NotBlank
		String cidade,
		@NotBlank
		@Pattern(regexp = "\\w{2}")
		String uf,
		@NotBlank
		@Pattern(regexp = "\\d{8}")
		String cep
		) {

}
