package br.com.escola.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/turma")
public class TurmaController {

	@Autowired
	private TurmaService turmaService;

	@GetMapping
	public Page<TurmaDadosListagem> listAll(@PageableDefault(size = 10, sort = {"codigo"}) Pageable paginacao) {
		return turmaService.findAll(paginacao);
	}
	
	@GetMapping("/codigo/{codigo}")
	@ResponseStatus(HttpStatus.OK)
	public Page<TurmaDadosListagem> buscarTodasPorCodigo(@PathVariable("codigo") String codigo, @PageableDefault(size = 10, sort = {"codigo"}) Pageable paginacao) {
		return turmaService.buscarTodasPorCodigo(codigo, paginacao);
	}
	
	
	@GetMapping("/{id}")
	public TurmaDadosCompleto findById(@PathVariable("id") Long id) {
		return turmaService.findById(id);
	}

	@GetMapping("/desativadas")
	public Page<TurmaDadosListagem> listAllDesativadas(@PageableDefault(size = 10, sort = {"codigo"}) Pageable paginacao) {
		return turmaService.findAllDesativadas(paginacao);
	}

	@PostMapping
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public TurmaDadosCompleto cadastrarturma(@RequestBody @Valid TurmaDadosCadastro dados) {
		return turmaService.create(dados);
	}
	
	@PutMapping
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public TurmaDadosCompleto alterarTurma(@RequestBody @Valid TurmaDadosAtualizacao dados){
		return turmaService.update(dados);
	}
	
	@PutMapping("/ativar/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public TurmaDadosCompleto ativar(@PathVariable("id") Long id) {
		return turmaService.ativar(id);
	}
	
	@PutMapping("/adiciona-disciplinas")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public TurmaDadosCompleto adicionarListaDeDisciplinas(@RequestBody @Valid TurmaDadosAdicaoDisciplinas dados ) {
		return turmaService.adicionarListaDeDisciplina(dados);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public void desativar(@PathVariable Long id) {
		turmaService.delete(id);
	}

}
