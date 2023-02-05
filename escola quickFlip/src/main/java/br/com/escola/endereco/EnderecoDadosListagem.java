package br.com.escola.endereco;

public record EnderecoDadosListagem(
		String logradouro,
		Integer numero,
		String complemento,
		String bairro,
		String cidade,
		String uf,
		String cep
		) {
	
	public EnderecoDadosListagem(Endereco endereco) {
		this(endereco.getLogradouro(), endereco.getNumero(), endereco.getComplemento(), endereco.getBairro(), endereco.getCidade(), endereco.getUf(), endereco.getCep());
	}
}
