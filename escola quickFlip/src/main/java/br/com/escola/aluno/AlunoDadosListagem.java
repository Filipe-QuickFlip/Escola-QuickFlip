package br.com.escola.aluno;

public record AlunoDadosListagem(Long id, String nome, String matricula, String telefone, String email) {
	
	public AlunoDadosListagem(Aluno aluno) {
		
		this(aluno.getId(), aluno.getNome(), aluno.getMatricula(), aluno.getTelefone(), aluno.getEmail());
		
	}
	
}
