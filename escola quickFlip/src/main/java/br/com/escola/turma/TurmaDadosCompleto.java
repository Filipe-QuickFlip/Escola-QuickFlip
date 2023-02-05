package br.com.escola.turma;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.escola.aluno.AlunoDadosListagem;
import br.com.escola.aula.AulaDadosListagem;
import br.com.escola.disciplina.DisciplinaDadosListagem;

public record TurmaDadosCompleto(Long id, String codigo, Periodo periodo, Serie serie,
		List<AlunoDadosListagem> alunos, List<AulaDadosListagem> aulas, List<DisciplinaDadosListagem> disciplinas) {

	public TurmaDadosCompleto(Turma turma) {
		this(turma.getId(), turma.getCodigo(), turma.getPeriodo(), turma.getSerie(),
				(turma.getAlunos() != null) ? turma.getAlunos().stream().map(a -> new AlunoDadosListagem(a)).collect(Collectors.toList()) : new ArrayList<>(),
				(turma.getAulas() != null) ? turma.getAulas().stream().map(a -> new AulaDadosListagem(a)).collect(Collectors.toList())  : new ArrayList<>(),
				(turma.getDisciplinas() != null) ? turma.getDisciplinas().stream().map(a -> new DisciplinaDadosListagem(a)).collect(Collectors.toList()) : new ArrayList<>());
	}

}
