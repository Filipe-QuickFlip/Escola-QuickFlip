package br.com.escola.aula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.escola.turma.Periodo;
import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/aula")
public class AulaController {
	
	@Autowired
	private AulaService aulaService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<AulaDadosListagem> verTodasAsAulas(@PageableDefault(size = 10, sort = {"dia"}) Pageable paginacao){
		return aulaService.verTodasAsAulas(paginacao);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AulaDadosCompleto verAulasPorId(@PathVariable Long id){
		return aulaService.buscarPorId(id);
	}
	
	@GetMapping("/professor/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Page<AulaDadosListagem> verAulasPorProfessor(@PageableDefault(size = 10, sort = {"dia"}) @PathVariable Long id, Pageable paginacao){
		return aulaService.verAulaPorProfessor(id,paginacao);
	}
	
	@GetMapping("/turma/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Page<AulaDadosListagem> verAulasPorTurma(@PageableDefault(size = 10, sort = {"dia"}) @PathVariable Long id, Pageable paginacao){
		return aulaService.verAulaPorTurma(id, paginacao);
	}
	
	@GetMapping("/disciplina/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Page<AulaDadosListagem> verAulasPorDisciplina(@PageableDefault(size = 10) @PathVariable Long id, Pageable paginacao){
		return aulaService.verAulaPorDisciplina(id, paginacao);
	}
	
	@GetMapping("/periodo/{periodo}")
	@ResponseStatus(HttpStatus.OK)
	public Page<AulaDadosListagem> verAulasPorPeriodo(@PathVariable("periodo") Periodo periodo, @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao){
		return aulaService.verAulasPorPeriodo(periodo, paginacao);
	}
	
	@GetMapping("/periodo/matutino")
	@ResponseStatus(HttpStatus.OK)
	public Page<AulaDadosListagem> verAulasMatutino(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao){
		return aulaService.verAulaPeriodoMatutino(paginacao);
	}
	
	@GetMapping("/periodo/vespertino")
	@ResponseStatus(HttpStatus.OK)
	public Page<AulaDadosListagem> verAulasVespertino(@PageableDefault(size = 10) Pageable paginacao){
		return aulaService.verAulaPeriodoVespertino(paginacao);
	}
	
	@GetMapping("/periodo/noturno")
	@ResponseStatus(HttpStatus.OK)
	public Page<AulaDadosListagem> verAulasNoturno(@PageableDefault(size = 10) Pageable paginacao){
		return aulaService.verAulaPeriodoNoturno(paginacao);
	}
	
	@GetMapping("/inativos")
	@ResponseStatus(HttpStatus.OK)
	public Page<AulaDadosListagem> verAulasInativas(@PageableDefault(size = 10) Pageable paginacao){
		return aulaService.verTodasAsAulasInativas(paginacao);
	}
	
	@PostMapping
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	public AulaDadosListagem cadastrarAula(@RequestBody AulaDadosCadastro dados){
		return aulaService.cadastrarAula(dados);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public void deletarAula(@PathVariable Long id){
		aulaService.deletarAula(id);
	}

}
