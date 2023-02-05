package br.com.escola.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Endereco {
	
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String cep;
	
	public Endereco(EnderecoDadosCadastro dados) {
		this.logradouro = dados.logradouro();
		this.numero = dados.numero();
		this.complemento = dados.complemento();
		this.bairro = dados.bairro();
		this.cidade = dados.cidade();
		this.uf = dados.uf();
		this.cep = dados.cep();
	}
	
	public void atualizar(EnderecoDadosAtualizacao dados) {
		if(dados.logradouro() != null) {
			this.logradouro = dados.logradouro();
		}
		if(dados.numero() != null) {
			this.numero = dados.numero();
		}
		if(dados.complemento() != null) {
			this.complemento = dados.complemento();
		}
		if(dados.bairro() != null) {
			this.bairro = dados.bairro();				
		}
		if(dados.cidade() != null) {
			this.cidade = dados.cidade();
		}
		if(dados.uf() != null) {
			this.uf = dados.uf();
		}
		if(dados.cep() != null) {
			this.cep = dados.cep();
		}
	}
	
}
