package br.com.escola.disciplina;

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
@RequestMapping("/disciplina")
public class DisciplinaController {
	
	@Autowired
	private DisciplinaService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<DisciplinaDadosListagem> buscarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return service.buscarTodos(paginacao);
	}
	
	@GetMapping("/nome/{nome}")
	@ResponseStatus(HttpStatus.OK)
	public Page<DisciplinaDadosListagem> buscarTodasPorNome(@PathVariable("nome") String nome, @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return service.buscarTodasPorNome(nome, paginacao);
	}
	
	@PostMapping
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	public DisciplinaDadosListagem cadastrarDisciplina(@RequestBody @Valid DisciplinaDadosCadastro dados) {
		return service.cadastarDisciplina(dados);
	}
	
	@PutMapping
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public DisciplinaDadosListagem atualizarDisciplina(@RequestBody @Valid DisciplinaDadosAtualizacao dados) {
		return service.atualizarDisciplina(dados);
	}
	
	@PutMapping("/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public DisciplinaDadosListagem reativarDisciplina(@PathVariable("id") Long id) {
		return service.reativarDisciplina(id);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public void desativarDisciplina(@PathVariable("id") Long id) {
		service.desativarDisciplina(id);
	}
}
