package br.com.escola.professor;

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
@RequestMapping("/professor")
public class ProfessorController {

	@Autowired
	private ProfessorService serviceProfessor;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<ProfessorDadosListagem> buscarTodos(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		return serviceProfessor.buscarTodos(paginacao);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProfessorDadosCompleto buscarPorId(@PathVariable("id") Long id) {
		return serviceProfessor.buscarPorId(id);
	}

	@GetMapping("/nome/{nome}")
	@ResponseStatus(HttpStatus.OK)
	public Page<ProfessorDadosListagem> buscarPorProfessoresPorNome(@PathVariable String nome, @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		return serviceProfessor.buscarProfessorPorNome(nome, paginacao);
	}

	@GetMapping("/formacao/{formacao}")
	@ResponseStatus(HttpStatus.OK)
	public Page<ProfessorDadosListagem> buscarPorProfessoresPorFormacao(@PathVariable String formacao,
			@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		return serviceProfessor.buscarProfessorPorFormacao(formacao, paginacao);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public ProfessorDadosCompleto cadastrarProfessor(@RequestBody @Valid ProfessorDadosCadastro dados) {
		return serviceProfessor.cadastrarProfessor(dados);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@Transactional
	public ProfessorDadosCompleto atualizarProfessor(@RequestBody @Valid ProfessorDadosAtualizacao dados) {
		return serviceProfessor.atualizar(dados);
	}

	@PutMapping("/id/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public ProfessorDadosCompleto reativarProfessor(@PathVariable("id") Long id) {
		return serviceProfessor.reativarProfessor(id);
	}
	
	@PutMapping("/adiciona-disciplinas")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public ProfessorDadosCompleto adicionarListaDeDisciplinas(@RequestBody @Valid ProfessorDadosAdicaoDisciplinas dados ) {
		return serviceProfessor.adicionarListaDeDisciplina(dados);
	}
	

	@DeleteMapping("/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public void desativar(@PathVariable Long id) {
		serviceProfessor.desativarProfessor(id);
	}
	
}
