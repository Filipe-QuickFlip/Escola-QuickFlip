package br.com.escola.aula;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.escola.disciplina.DisciplinaDadosListagem;
import br.com.escola.professor.ProfessorDadosListagem;
import br.com.escola.turma.TurmaDadosListagem;

public record AulaDadosCompleto(
		Long id,
		@JsonFormat(pattern = "dd/MM/yyyy") LocalDate dia, 
		Ordem ordem,
		DisciplinaDadosListagem disicplina,
		ProfessorDadosListagem professor,
		TurmaDadosListagem turma) {
		
	public AulaDadosCompleto(Aula aula) {
		this(
				aula.getId(), 
				aula.getDia(), 
				aula.getOrdem(),
				new DisciplinaDadosListagem(aula.getDisciplina()),
				new ProfessorDadosListagem(aula.getProfessor()),
				new TurmaDadosListagem(aula.getTurma()));
	}
}
