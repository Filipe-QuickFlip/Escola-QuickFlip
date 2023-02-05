package br.com.escola.aluno;

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
@RequestMapping("/aluno")
public class AlunoController {
	
	@Autowired
	private AlunoService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<AlunoDadosListagem> buscarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return service.buscarTodos(paginacao);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AlunoDadosCompleto buscarPorId(@PathVariable("id") Long id) {
		return service.buscarPorId(id);
	}
	
	@GetMapping("/nome/{nome}")
	@ResponseStatus(HttpStatus.OK)
	public Page<AlunoDadosListagem> buscarTodosPorNome(@PathVariable("nome") String nome, @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return service.buscarTodosPorNome(nome, paginacao);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public AlunoDadosCompleto cadastrarAluno(@RequestBody @Valid AlunoDadosCadastro dados) {
		return service.cadastarAluno(dados);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@Transactional
	public AlunoDadosCompleto atualizarAluno(@RequestBody @Valid AlunoDadosAtualizacao dados) {
		return service.atualizarAluno(dados);
	}
	
	@PutMapping("/matricular")
	@ResponseStatus(HttpStatus.OK)
	@Transactional
	public String matricularAluno(@RequestBody @Valid AlunoDadosMatriculaTurma dados) {
		return service.matricularAluno(dados);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Transactional
	public AlunoDadosCompleto reativarAluno(@PathVariable("id") Long id) {
		return service.reativarAluno(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Transactional
	public void desativarAluno(@RequestBody @PathVariable("id") Long id) {
		service.desativarAluno(id);
	}
}
