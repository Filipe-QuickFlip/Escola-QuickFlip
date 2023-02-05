package br.com.escola.professor;

public record ProfessorDadosListagem(
		Long id,
		String nome,
		String matricula,
		String telefone,
		String email,
		String formacao
		) {
	
	public ProfessorDadosListagem(Professor professor) {
		this(professor.getId(), professor.getNome(), professor.getMatricula(), professor.getTelefone(), professor.getEmail(), professor.getFormacao());
	}

}

