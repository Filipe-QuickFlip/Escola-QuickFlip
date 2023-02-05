package br.com.escola.endereco;

import jakarta.validation.constraints.Pattern;

public record EnderecoDadosAtualizacao(
		String logradouro,
		Integer numero,
		String complemento,
		String bairro,
		String cidade,
		@Pattern(regexp = "\\w{2}")
		String uf,
		@Pattern(regexp = "\\d{8}")
		String cep
		) {

}
