package br.com.escola.aula;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.escola.disciplina.Disciplina;
import br.com.escola.professor.Professor;
import br.com.escola.turma.Turma;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "aula")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dia;
	
	@Enumerated(EnumType.STRING)
	private Ordem ordem;
	private Boolean ativo;

	@ManyToOne
	@JoinColumn(name = "id_disciplina", referencedColumnName = "id")
	private Disciplina disciplina;

	@ManyToOne
	@JoinColumn(name = "id_professor", referencedColumnName = "id")
	private Professor professor;

	@ManyToOne
	@JoinColumn(name = "id_turma", referencedColumnName = "id")
	private Turma turma;

	public void desativar() {
		this.ativo = false;
	}

	public void reativar() {
		this.ativo = true;
	}

	public Aula(AulaDadosCadastro dados, Disciplina disciplina, Professor professor, Turma turma) {
		this.dia = dados.dia();
		this.ordem = dados.ordem();
		this.ativo = true;
		this.disciplina = disciplina;
		this.professor = professor;
		this.turma = turma;
	}

}
