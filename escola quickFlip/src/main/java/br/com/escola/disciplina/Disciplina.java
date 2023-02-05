package br.com.escola.disciplina;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.escola.professor.Professor;
import br.com.escola.turma.Turma;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "disciplina")
@Entity 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nome;
	
	private BigDecimal cargaHoraria;
	
	private Boolean ativo;
	
	@ManyToMany(mappedBy = "disciplinas")
	@JsonIgnore
    private List<Turma> turmas;
	
	
	@ManyToMany(mappedBy = "disciplinas")
	@JsonIgnore
    private List<Professor> professores;


	public Disciplina(DisciplinaDadosCadastro dados) {
		this.nome = dados.nome();
		this.cargaHoraria = dados.cargaHoraria();
		this.ativo = true;
	}
	
	public void atualizar(DisciplinaDadosAtualizacao dados) {
		if(dados.nome() != null) {
			this.nome = dados.nome();
		}
		if(dados.cargaHoraria() != null) {
			this.cargaHoraria = dados.cargaHoraria();
		}
	}
	
	public void desativar() {
		this.ativo = false;
	}
	
	public void ativar() {
		this.ativo = true;
	}
	
}
