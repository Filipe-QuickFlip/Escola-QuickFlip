package br.com.escola.aluno;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.escola.endereco.Endereco;
import br.com.escola.turma.Turma;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "aluno")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Aluno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Column(unique = true)
	private String matricula; //Matrícula é composta por um código de 6 números
	
	@Column(unique = true)
	private String cpf;
	
	private String telefone;
	
	private String email;
	
	@Embedded
	private Endereco endereco;
	
	private Boolean ativo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_turma", referencedColumnName = "id")
	@JsonIgnore
	private Turma turma;
	
	public Aluno(AlunoDadosCadastro dados) {
		this.nome = dados.nome();
		this.matricula = dados.matricula();
		this.cpf = dados.cpf();
		this.telefone = dados.telefone();
		this.email = dados.email();
		this.endereco = new Endereco(dados.endereco());
		this.ativo = true;
	}
	
	public void matricularEmTurma(Turma turma) {
		this.turma = turma;
	}
	
	public void atualizar(AlunoDadosAtualizacao dados) {
		if(dados.nome() != null) {
			this.setNome(dados.nome());
		}
		if(dados.email() != null) {
			this.setEmail(dados.email());
		}
		if(dados.telefone() != null) {
			this.setTelefone(dados.telefone());
		}
		if(dados.endereco() != null) {
			this.getEndereco().atualizar(dados.endereco());
		}
	}
	
	public void desativar() {
		this.ativo = false;
	}
	
	public void ativar() {
		this.ativo = true;
	}

	
	
}
