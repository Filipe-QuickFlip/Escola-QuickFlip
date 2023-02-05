package br.com.escola.professor;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.escola.aula.Aula;
import br.com.escola.disciplina.Disciplina;
import br.com.escola.endereco.Endereco;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "professor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Column(unique = true)
	private String matricula;
	
	@Column(unique = true)
	private String cpf;
	
	private String telefone;
	
	private String email;
	
	private BigDecimal salario;
	
	private String formacao;
	
	@Embedded
	private Endereco endereco;
	
	private Boolean ativo;
	
	@ManyToMany
    @JoinTable(
            name = "disciplina_professor",
            joinColumns = @JoinColumn(name = "id_professor"),
            inverseJoinColumns = @JoinColumn(name = "id_disciplina")
    )
	private List<Disciplina> disciplinas;
	
	@OneToMany(mappedBy = "professor")
	@JsonIgnore
	private List<Aula> aulas;
	
	public Professor(ProfessorDadosCadastro dados) {
		this.nome = dados.nome();
		this.matricula = dados.matricula();
		this.cpf = dados.cpf();
		this.telefone = dados.telefone();
		this.email = dados.email();
		this.salario = dados.salario();
		this.formacao = dados.formacao();
		this.endereco = new Endereco(dados.endereco());
		this.ativo = true;
	}
	
	public void atualizar(ProfessorDadosAtualizacao dados) {
		if(dados.nome()!= null) {
			this.nome=dados.nome();
		}
		if(dados.email() != null) {
			this.email=dados.email();
		}
		if(dados.telefone() != null) {
			this.telefone= dados.telefone();
		}
		if(dados.salario() != null) {
			this.salario= dados.salario();
		}
		if(dados.formacao() != null) {
			this.formacao = dados.formacao();
		}
		if(dados.endereco() != null) {
			this.endereco.atualizar(dados.endereco());
		}
	}
	
	public void adicionarDisciplina(Disciplina disciplina) {
		this.disciplinas.add(disciplina);
	}


	public void desativar() {
		this.ativo = false;
	}
	
	public void ativar() {
		this.ativo = true;
	}


}
