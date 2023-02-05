package br.com.escola.turma;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.escola.aluno.Aluno;
import br.com.escola.aula.Aula;
import br.com.escola.disciplina.Disciplina;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "turma")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String codigo; // Código formado por uma letra e um número

	@Enumerated(EnumType.STRING)
	private Periodo periodo;

	@Enumerated(EnumType.STRING)
	private Serie serie;

	private Boolean ativo;

	@OneToMany(mappedBy = "turma")
	@JsonIgnore
	private List<Aluno> alunos;
	
	@ManyToMany
    @JoinTable(
            name = "disciplina_turma",
            joinColumns = @JoinColumn(name = "id_turma"),
            inverseJoinColumns = @JoinColumn(name = "id_disciplina")
    )
	private List<Disciplina> disciplinas;

	@OneToMany(mappedBy = "turma")
	@JsonIgnore
	private List<Aula> aulas;

	public Turma(TurmaDadosCadastro dados) {
		this.codigo = dados.codigo();
		this.periodo = dados.periodo();
		this.serie = dados.serie();
		this.disciplinas = new ArrayList<>();
		this.ativo = true;
	}

	public void adicionarAluno(Aluno aluno) {
		this.alunos.add(aluno);
	}

	public void adicionarAulas(Aula aula) {
		this.aulas.add(aula);
	}

	public void adicionarDisciplina(Disciplina disciplina) {
		this.disciplinas.add(disciplina);
	}

	public void atualizar(TurmaDadosAtualizacao dados) {
		if(dados.codigo() != null) {
			this.codigo = dados.codigo();
		}
		if (dados.periodo() != null) {
			this.periodo = dados.periodo();
		}
		if (dados.serie() != null) {
			this.serie = dados.serie();
		}
	}

	public void desativar() {
		this.ativo = false;
	}

	public void ativar() {
		this.ativo = true;
	}

}
