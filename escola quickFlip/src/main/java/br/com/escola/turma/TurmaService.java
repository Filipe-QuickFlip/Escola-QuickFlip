package br.com.escola.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.escola.disciplina.Disciplina;
import br.com.escola.disciplina.DisciplinaService;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private DisciplinaService disciplinaService;

	public Page<TurmaDadosListagem> findAll(Pageable paginacao) {
		return turmaRepository.findAllByAtivoTrue(paginacao).map(t -> new TurmaDadosListagem(t));
	}
	
	public Page<TurmaDadosListagem> buscarTodasPorCodigo(String codigo, Pageable paginacao) {
		return turmaRepository.findAllByCodigoContainsIgnoreCaseAndAtivoTrue(codigo, paginacao).map(t -> new TurmaDadosListagem(t));
	}
	
	public TurmaDadosCompleto findById(Long id) {
		return new TurmaDadosCompleto(turmaRepository.findById(id).get());
	}

	public Page<TurmaDadosListagem> findAllDesativadas(Pageable paginacao) {

		return turmaRepository.findAllByAtivoFalse(paginacao).map(t -> new TurmaDadosListagem(t));
	}

	public TurmaDadosCompleto create(TurmaDadosCadastro dados) {
		if(turmaRepository.findByCodigo(dados.codigo()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Código já cadastrado!");
		}
		
		Turma turma = new Turma(dados);
		turmaRepository.save(turma);
		return new TurmaDadosCompleto(turma);
	} 

	public TurmaDadosCompleto update(TurmaDadosAtualizacao dados) {
		Turma turma = buscarEntidadeAtiva(dados.id());
		turma.atualizar(dados);
		return new TurmaDadosCompleto(turma);
	}

	public TurmaDadosCompleto ativar(Long id) {
		Turma turma = turmaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada!"));
		
		if (turma.getAtivo()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Turma já ativa, favor informar id de uma turma desativada!");
		}
		
		turma.ativar();
		return new TurmaDadosCompleto(turma);
	}

	public TurmaDadosCompleto adicionarListaDeDisciplina(TurmaDadosAdicaoDisciplinas dados) {
		Turma turma = buscarEntidadeAtiva(dados.idTurma());
		
		dados.idDisciplinas().stream().forEach((id) -> {
			Disciplina disciplina = disciplinaService.buscarEntidadeAtiva(id);
			
			if(!turma.getDisciplinas().stream().anyMatch(d -> d.getNome().equals(disciplina.getNome()))) {	
				turma.adicionarDisciplina(disciplina);
			}	
		});
		
		return new TurmaDadosCompleto(turma);
	}
	
	public void delete(Long id) {
		Turma turma = buscarEntidadeAtiva(id);
		if(turma.getAlunos() != null && !turma.getAlunos().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Turma contém alunos, não pode ser deletada!");
		}
		turma.desativar();
	}
	
	public Turma buscarEntidadeAtiva(Long id) {
		return turmaRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma inativa ou inexistente!"));
	}

}
