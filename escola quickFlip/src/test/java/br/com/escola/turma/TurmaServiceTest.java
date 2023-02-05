package br.com.escola.turma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.escola.aluno.Aluno;
import br.com.escola.disciplina.Disciplina;
import br.com.escola.disciplina.DisciplinaRepository;
import br.com.escola.disciplina.DisciplinaService;
import br.com.escola.turma.Periodo;
import br.com.escola.turma.Serie;
import br.com.escola.turma.Turma;
import br.com.escola.turma.TurmaDadosAdicaoDisciplinas;
import br.com.escola.turma.TurmaDadosAtualizacao;
import br.com.escola.turma.TurmaDadosCadastro;
import br.com.escola.turma.TurmaDadosCompleto;
import br.com.escola.turma.TurmaRepository;
import br.com.escola.turma.TurmaService;

@ExtendWith(MockitoExtension.class)
class TurmaServiceTest {

	@InjectMocks
	private TurmaService service;

	@Mock
	private TurmaRepository repository;

	@Mock
	private DisciplinaService disciplinaService;

	@Mock
	private DisciplinaRepository disciplinaRepository;

	@Test
	void deveriaJogarExcecaoQuandoNaoEncontrarTurma() {
		when(repository.findByIdAndAtivoTrue(any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

		assertThrows(ResponseStatusException.class, () -> service.buscarEntidadeAtiva(any()));
	}

	@Test
	void deveriaAdicionarUmaDisciplinaNaTurma() {
		Turma turma = entidadeTurma();
		Disciplina disciplina = entidadeDisciplina();

		when(repository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(turma));

		when(disciplinaService.buscarEntidadeAtiva(anyLong())).thenReturn(disciplina);

		TurmaDadosAdicaoDisciplinas dados = new TurmaDadosAdicaoDisciplinas(1l, new ArrayList<>() {
			{
				add(1l);
			}
		}

		); 

		service.adicionarListaDeDisciplina(dados);
		assertEquals("Informática", turma.getDisciplinas().get(0).getNome());
	}

	@Test
	void deveriaAtivarUmaTurma() {
		Turma turma = entidadeTurma();
		turma.setAtivo(false);

		when(repository.findById(anyLong())).thenReturn(Optional.of(turma));

		service.ativar(anyLong());

		assertTrue(turma.getAtivo());
	}

	@Test
	void deveriaRetornarErrorTurmaJaAtivada() {
		Turma turma = entidadeTurma();
		when(repository.findById(anyLong())).thenReturn(Optional.of(turma));

		assertThrows(ResponseStatusException.class, () -> service.ativar(anyLong()));
	}
	
	@Test
	void deveriaRetornarErrorTurmaNaoEncontrada() {

		assertThrows(ResponseStatusException.class, () -> service.ativar(1l));
	}

	@Test
	void deveriaRetornarTurma() {
		Turma turma = entidadeTurma();
		when(repository.findById(any())).thenReturn(Optional.of(turma));
		TurmaDadosCompleto response = service.findById(1l);
		assertEquals(1L, response.id());

	}

	@Test
	void deveriaDesativarUmTurma() {
		Turma turma = entidadeTurma();
		when(repository.findByIdAndAtivoTrue(any())).thenReturn(Optional.of(turma));
		service.delete(1L);

		assertFalse(turma.getAtivo());
	}

	@Test
	void deveriaRetornarErrorQuandoTurmaTemAlunosAtivos() {
		Aluno aluno = new Aluno();
		aluno.setAtivo(true);
		Turma turma = entidadeTurma();
		turma.adicionarAluno(aluno);
		assertThrows(ResponseStatusException.class, () -> service.delete(1L));
	}
	
	@Test
	void deveriaAtualizarUmaTurma() {
		Turma turma = entidadeTurma();
		when(repository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(turma));
		
		TurmaDadosAtualizacao dadosAtualizacao = new TurmaDadosAtualizacao(1l, "M9", null, null);
		
		
		service.update(dadosAtualizacao);
		
		
		assertEquals(dadosAtualizacao.codigo(), turma.getCodigo());
	}
	
	@Test
	void deveriaRetornarExecaoAoCriarTurmaComCodigoJaExistente() {
		
		Turma turma = entidadeTurma();
		
		when(repository.findByCodigo(any())).thenReturn(Optional.of(turma));
		
		TurmaDadosCadastro novaturma = new TurmaDadosCadastro("M1", Periodo.MATUTINO, Serie.SEGUNDO_ANO);
		
		assertThrows(ResponseStatusException.class, () -> service.create(novaturma));
		
		
	}

	private Turma entidadeTurma() {
		return Turma.builder().id(1L).codigo("M1").periodo(Periodo.MATUTINO).serie(Serie.PRIMEIRO_ANO).ativo(true)
				.alunos(new ArrayList<>()).aulas(new ArrayList<>()).disciplinas(new ArrayList<>()).build();
	}

	private Disciplina entidadeDisciplina() {
		return Disciplina.builder().id(1L).nome("Informática").cargaHoraria(new BigDecimal("400.0")).ativo(true)
				.build();
	}

}
