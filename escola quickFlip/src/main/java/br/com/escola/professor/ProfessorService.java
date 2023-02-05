package br.com.escola.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.escola.disciplina.Disciplina;
import br.com.escola.disciplina.DisciplinaService;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private DisciplinaService disciplinaService;
	
	public Page<ProfessorDadosListagem> buscarTodos(Pageable paginacao) {
		return professorRepository.findAllByAtivoTrue(paginacao).map(p -> new ProfessorDadosListagem(p));
	}

	public ProfessorDadosCompleto buscarPorId(Long id) {
		return new ProfessorDadosCompleto(buscarEntidadeAtiva(id));
	}

	public Page<ProfessorDadosListagem> buscarProfessorPorNome(String nome, Pageable paginacao) {
		return professorRepository.findAllByNomeContainsIgnoreCaseAndAtivoTrue(nome, paginacao).map(p -> new ProfessorDadosListagem(p));
	}

	public Page<ProfessorDadosListagem> buscarProfessorPorFormacao(String formacao, Pageable paginacao) {
		return professorRepository.findAllByFormacaoContainsIgnoreCaseAndAtivoTrue(formacao, paginacao).map(p -> new ProfessorDadosListagem(p));
	}

	public ProfessorDadosCompleto cadastrarProfessor(ProfessorDadosCadastro dadosProfesor) {
		if(professorRepository.findByCpf(dadosProfesor.cpf()).isPresent()){
			throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado!");
		}
		
		if(professorRepository.findByMatricula(dadosProfesor.matricula()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Matrícula já cadastrada!");
		}
		
		Professor professor = new Professor(dadosProfesor);
		professorRepository.save(professor);
		return new ProfessorDadosCompleto(professor);
	}
	
	public ProfessorDadosCompleto atualizar(ProfessorDadosAtualizacao dados) {
		Professor professor = buscarEntidadeAtiva(dados.id());
		professor.atualizar(dados);
		return new ProfessorDadosCompleto(professor);
	}

	public ProfessorDadosCompleto reativarProfessor(Long id) {
		Professor professor = professorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado!"));
		
		if(professor.getAtivo()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Professor já ativo, favor informar id de um professor desativado!");
		}
		
		professor.ativar();
		return new ProfessorDadosCompleto(professor);
	}
	
	public void desativarProfessor(Long id) {
		Professor professor = buscarEntidadeAtiva(id);
		professor.desativar();
	}
	
	public ProfessorDadosCompleto adicionarListaDeDisciplina(ProfessorDadosAdicaoDisciplinas dados) {
		Professor professor = buscarEntidadeAtiva(dados.idProfessor());
		
		dados.idDisciplinas().stream().forEach((id) -> {
			Disciplina disciplina = disciplinaService.buscarEntidadeAtiva(id);
			
			if(!professor.getDisciplinas().stream().anyMatch(d -> d.getNome().equals(disciplina.getNome()))) {	
				professor.adicionarDisciplina(disciplina);
			}	
			
		});
		
		return new ProfessorDadosCompleto(professor);
	}
	
	public Professor buscarEntidadeAtiva(Long id) {
		return professorRepository.findByIdAndAtivoTrue(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor inativo ou inexistente!"));
	}

}
