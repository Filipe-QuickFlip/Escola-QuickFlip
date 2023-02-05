package br.com.escola.aula;

import java.math.BigDecimal;
import java.time.DayOfWeek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.escola.disciplina.Disciplina;
import br.com.escola.disciplina.DisciplinaService;
import br.com.escola.professor.Professor;
import br.com.escola.professor.ProfessorService;
import br.com.escola.turma.Periodo;
import br.com.escola.turma.Turma;
import br.com.escola.turma.TurmaService;

@Service
public class AulaService {
	
	@Autowired
	private AulaRepository aulaRepository;
	
	@Autowired 
	private DisciplinaService disciplinaService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private TurmaService turmaService;
	
	public Page<AulaDadosListagem> verTodasAsAulas(Pageable paginacao){
		return aulaRepository.findByAtivoTrue(paginacao).map(AulaDadosListagem::new);
	}
	
	public AulaDadosCompleto buscarPorId(Long id){
		return new AulaDadosCompleto(buscarEntidadeAtiva(id));
	}
	
	public Page<AulaDadosListagem> verAulaPorProfessor(Long id, Pageable paginacao){
		return aulaRepository.findAulaByProfessorIdAndAtivoTrue(professorService.buscarEntidadeAtiva(id).getId(), paginacao).map(AulaDadosListagem::new);
	}
	
	public Page<AulaDadosListagem> verAulaPorTurma(Long id, Pageable paginacao){
		return aulaRepository.findAulaByTurmaIdAndAtivoTrue(turmaService.buscarEntidadeAtiva(id).getId(), paginacao).map(AulaDadosListagem::new);
	}
	
	public Page<AulaDadosListagem> verAulaPorDisciplina(Long id, Pageable paginacao){
		return aulaRepository.findAulaByDisciplinaIdAndAtivoTrue(disciplinaService.buscarEntidadeAtiva(id).getId(), paginacao).map(AulaDadosListagem::new);
	}
	
	public Page<AulaDadosListagem> verTodasAsAulasInativas(Pageable paginacao){
		return aulaRepository.findByAtivoFalse(paginacao).map(AulaDadosListagem::new);
	}
	
	public Page<AulaDadosListagem> verAulaPeriodoMatutino(Pageable paginacao){
		return aulaRepository.findAulaByTurmaPeriodoMatutino(paginacao).map(AulaDadosListagem::new);
	}
	
	public Page<AulaDadosListagem> verAulaPeriodoVespertino(Pageable paginacao){
		return aulaRepository.findAulaByTurmaPeriodoVespertino(paginacao).map(AulaDadosListagem::new);
	}
	
	public Page<AulaDadosListagem> verAulaPeriodoNoturno(Pageable paginacao){
		return aulaRepository.findAulaByTurmaPeriodoNoturno(paginacao).map(AulaDadosListagem::new);
	}
	
	public AulaDadosListagem cadastrarAula(AulaDadosCadastro dados) {
		Disciplina disciplina = disciplinaService.buscarEntidadeAtiva(dados.idDisciplina());
		Professor professor = professorService.buscarEntidadeAtiva(dados.idProfessor());
		Turma turma = turmaService.buscarEntidadeAtiva(dados.idTurma());
		
		if(dados.dia().getDayOfWeek().equals(DayOfWeek.SATURDAY) || dados.dia().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Aulas não podem ocorrer durante os fins de semana!");
		}
		
		if(aulaRepository.findByIdTurmaAndOrdemAula(turma.getId(), dados.ordem().toString(), dados.dia()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Essa turma já tem aula agendada para o mesmo momento!");
		}
		
		if(aulaRepository.findByIdProfessorAndPeriodoTurmaAndOrdemAula(professor.getId(), turma.getPeriodo().toString(), dados.ordem().toString(), dados.dia()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Esse professor já tem aula agendada para o mesmo momento!");
		}
		
		if(!turma.getDisciplinas().stream().anyMatch(d -> d.getNome().equals(disciplina.getNome()))) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Essa disciplina não está na grade dessa turma!");
		}
		
		BigDecimal total = new BigDecimal(aulaRepository.totalDeAulasPorTurmaPorDisciplina(turma.getId(), disciplina.getId()));
		
		if(disciplina.getCargaHoraria().subtract(total).compareTo(BigDecimal.ZERO) <= 0) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Essa turma já supriu a carga horária dessa disciplina!");
		}
		
		if(!professor.getDisciplinas().stream().anyMatch(d -> d.getNome().equals(disciplina.getNome()))) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esse professor não é indicado para lecionar essa disciplina!");
		}
		
		
		Aula aula = new Aula(dados, disciplina, professor, turma);
		aulaRepository.save(aula);
		return new AulaDadosListagem(aula);
	}
	
	public void deletarAula(Long id) {
		Aula aula = buscarEntidadeAtiva(id);
		aula.desativar();
	}
	
	public Aula buscarEntidadeAtiva(Long id) {
		return aulaRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula inativa ou inexistente!")); 
	}

	public Page<AulaDadosListagem> verAulasPorPeriodo(Periodo periodo, Pageable paginacao) {
		return aulaRepository.findByTurmaPeriodoAndAtivoTrue(periodo, paginacao).map(AulaDadosListagem::new);
	}
	
}
