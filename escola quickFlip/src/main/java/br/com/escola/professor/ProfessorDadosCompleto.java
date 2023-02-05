package br.com.escola.professor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.escola.disciplina.DisciplinaDadosListagem;
import br.com.escola.endereco.EnderecoDadosListagem;

public record ProfessorDadosCompleto(
		Long id,
		String nome,
		String matricula,
		String cpf,
		String telefone,
		String email,
		BigDecimal salario,
		String formacao,
		EnderecoDadosListagem endereco,
		List<DisciplinaDadosListagem> disciplinas
		) {
	
	public ProfessorDadosCompleto(Professor professor) {
		this(professor.getId(), 
				professor.getNome(), 
				professor.getMatricula(), 
				professor.getCpf(), 
				professor.getTelefone(), 
				professor.getEmail(), 
				professor.getSalario(), 
				professor.getFormacao(), 
				new EnderecoDadosListagem(professor.getEndereco()),
				(professor.getDisciplinas() != null) ? professor.getDisciplinas().stream().map(d -> new DisciplinaDadosListagem(d)).collect(Collectors.toList()) : new ArrayList<>());
	}

}

