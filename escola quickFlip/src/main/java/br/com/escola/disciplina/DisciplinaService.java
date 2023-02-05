package br.com.escola.disciplina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DisciplinaService {
	
	@Autowired
	private DisciplinaRepository repository;
	
	public Page<DisciplinaDadosListagem> buscarTodos(Pageable paginacao) {
		return repository.findAllByAtivoTrue(paginacao).map(d -> new DisciplinaDadosListagem(d));
	}
	
	public DisciplinaDadosListagem cadastarDisciplina(DisciplinaDadosCadastro dados) {
		if(repository.findByNome(dados.nome()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Disciplina já cadastrada!");
		} 
		
		Disciplina disciplina = new Disciplina(dados);
		repository.save(disciplina);
		
		return new DisciplinaDadosListagem(disciplina);
	}

	public DisciplinaDadosListagem atualizarDisciplina(DisciplinaDadosAtualizacao dados) {
		Disciplina disciplina = buscarEntidadeAtiva(dados.id());
		disciplina.atualizar(dados);
		return new DisciplinaDadosListagem(disciplina);
	}

	public DisciplinaDadosListagem reativarDisciplina(Long id) {
		Disciplina disciplina = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada!"));
		
		if (disciplina.getAtivo()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Disciplina já ativa, favor informar id de uma disciplina desativada!");
		}
		
		disciplina.ativar();
		return new DisciplinaDadosListagem(disciplina);
	}
	
	public void desativarDisciplina(Long id) {
		Disciplina disciplina = buscarEntidadeAtiva(id);
		disciplina.desativar();
	} 
	
	public Disciplina buscarEntidadeAtiva(Long id) {
		return repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina inativa ou inexistente!")); 
	}

	public Page<DisciplinaDadosListagem> buscarTodasPorNome(String nome, Pageable paginacao) {
		return repository.findAllByNomeContainsIgnoreCaseAndAtivoTrue(nome, paginacao).map(d -> new DisciplinaDadosListagem(d));
	}

}
