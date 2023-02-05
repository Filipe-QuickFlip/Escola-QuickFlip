package br.com.escola.disciplina;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.escola.disciplina.Disciplina;
import br.com.escola.disciplina.DisciplinaDadosAtualizacao;
import br.com.escola.disciplina.DisciplinaDadosCadastro;
import br.com.escola.disciplina.DisciplinaRepository;
import br.com.escola.disciplina.DisciplinaService;

@ExtendWith(MockitoExtension.class)
class DisciplinaServiceTest {
	
	@InjectMocks
	private DisciplinaService service;
	
	@Mock
	private DisciplinaRepository repository;
	
	@Test
	void deveriaJogarExcecaoQuandoNaoEncontrarDisciplina() {
		when(repository.findByIdAndAtivoTrue(any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		assertThrows(ResponseStatusException.class, () -> service.buscarEntidadeAtiva(any()));
	}
	
	@Test
	void deveriaJogarExcecaoDeConflitoQuandoCadastrarDisciplinaComMesmoNomeDeOutraDisciplinaJaCadastrada() {
		Disciplina disciplina = entidadeDisciplina();
		
		when(repository.findByNome(any())).thenReturn(Optional.of(disciplina));
		
		DisciplinaDadosCadastro novaDisciplina = new DisciplinaDadosCadastro("Matemática", new BigDecimal("400.00"));
		
		assertThrows(ResponseStatusException.class, () -> service.cadastarDisciplina(novaDisciplina));
	}
	 

	@Test
	void deveriaAtualizarUmaDisciplina() {
		Disciplina disciplina = entidadeDisciplina();
		when(repository.findByIdAndAtivoTrue(any())).thenReturn(Optional.of(disciplina));
		
		DisciplinaDadosAtualizacao dadosAtualizacao = new DisciplinaDadosAtualizacao(1L, "Língua Portuguesa", new BigDecimal("200.00"));
		
		service.atualizarDisciplina(dadosAtualizacao);
		
		assertEquals(dadosAtualizacao.nome(), disciplina.getNome());
		assertEquals(dadosAtualizacao.cargaHoraria(), disciplina.getCargaHoraria());
	}
	
	@Test
	void deveriaReativarUmaDisciplina() {
		Disciplina disciplina = entidadeDisciplina();
		disciplina.desativar();
		
		when(repository.findById(any())).thenReturn(Optional.of(disciplina));
		
		
		service.reativarDisciplina(1L);
		
		assertTrue(disciplina.getAtivo());
	}
	
	@Test
	void deveriaDesativarUmaDisciplina() {
		Disciplina disciplina = entidadeDisciplina();
		when(repository.findByIdAndAtivoTrue(any())).thenReturn(Optional.of(disciplina));
		
		service.desativarDisciplina(1L);
		
		assertFalse(disciplina.getAtivo());
	}
	
	private Disciplina entidadeDisciplina() {
		return Disciplina.builder()
				.id(1L)
				.nome("Matemática")
				.cargaHoraria(new BigDecimal("300.50"))
				.ativo(true)
				.build();
	}

}
