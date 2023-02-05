package br.com.escola.aula;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.escola.aula.Aula;
import br.com.escola.aula.AulaDadosCadastro;
import br.com.escola.aula.AulaRepository;
import br.com.escola.aula.AulaService;
import br.com.escola.aula.Ordem;
import br.com.escola.disciplina.Disciplina;
import br.com.escola.disciplina.DisciplinaService;
import br.com.escola.endereco.Endereco;
import br.com.escola.professor.Professor;
import br.com.escola.professor.ProfessorService;
import br.com.escola.turma.Periodo;
import br.com.escola.turma.Serie;
import br.com.escola.turma.Turma;
import br.com.escola.turma.TurmaService;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {
	
	@InjectMocks
	private AulaService aulaService;

	@Mock
	private AulaRepository aulaRepository;
	
	@Mock
	private DisciplinaService disciplinaService;
	
	@Mock
	private ProfessorService professorService;
	
	@Mock
	private TurmaService turmaService;
	
	@Test
	void deveriaJogarExcecaoQuandoNaoEncontrarAulaPorIdAtiva() {
		when(aulaRepository.findByIdAndAtivoTrue(anyLong())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
		assertThrows(ResponseStatusException.class, () -> aulaService.buscarEntidadeAtiva(1L));
	}
	
	@Test
	void deveriaCadastrarNovaAula() {
		
		Turma turma = entidadeTurma();
		when(turmaService.buscarEntidadeAtiva(any())).thenReturn(turma);
		
		Professor professor = entidadeProfessor();
		when(professorService.buscarEntidadeAtiva(any())).thenReturn(professor);
		
		Disciplina disciplina = entidadeDisciplina();
		when(disciplinaService.buscarEntidadeAtiva(any())).thenReturn(disciplina);
		
		AulaDadosCadastro dados = new AulaDadosCadastro(LocalDate.of(2023, 1, 19), Ordem.PRIMEIRA_AULA, turma.getId(), professor.getId(), disciplina.getId());
		
		Aula aula = new Aula(dados, disciplina, professor, turma);
		
		aulaService.cadastrarAula(dados);
		
		verify(aulaRepository).save(aula);
	}
	
	@Test
	void deveriaJogarExcecaoQuandoCadastrarAulaNoFimDeSemana() {
		
		Turma turma = entidadeTurma();
		when(turmaService.buscarEntidadeAtiva(any())).thenReturn(turma);
		
		Professor professor = entidadeProfessor();
		when(professorService.buscarEntidadeAtiva(any())).thenReturn(professor);
		
		Disciplina disciplina = entidadeDisciplina();
		when(disciplinaService.buscarEntidadeAtiva(any())).thenReturn(disciplina);
		
		AulaDadosCadastro dados = new AulaDadosCadastro(LocalDate.of(2023, 1, 21), Ordem.PRIMEIRA_AULA, turma.getId(), professor.getId(), disciplina.getId());
		
		assertThrows(ResponseStatusException.class, () -> aulaService.cadastrarAula(dados));
		
		verify(aulaRepository, never()).save(any());
	}
	
	@Test
	void deveriaJogarExcecaoQuandoCadastrarAulaDeTurmaComAulaAgendadaParaOMesmoHorario() {
		
		Turma turma = entidadeTurma();
		when(turmaService.buscarEntidadeAtiva(any())).thenReturn(turma);
		
		Professor professor = entidadeProfessor();
		when(professorService.buscarEntidadeAtiva(any())).thenReturn(professor);
		
		Disciplina disciplina = entidadeDisciplina();
		when(disciplinaService.buscarEntidadeAtiva(any())).thenReturn(disciplina);
		
		AulaDadosCadastro dados = new AulaDadosCadastro(LocalDate.of(2023, 1, 19), Ordem.PRIMEIRA_AULA, turma.getId(), professor.getId(), disciplina.getId());
		
		when(aulaRepository.findByIdTurmaAndOrdemAula(any(), any(), any())).thenReturn(Optional.of(Aula.builder().turma(turma).dia(LocalDate.of(2023, 1, 19)).ordem(Ordem.PRIMEIRA_AULA).build()));
		
		assertThrows(ResponseStatusException.class, () -> aulaService.cadastrarAula(dados));
		
		verify(aulaRepository, never()).save(any());
	}
	
	@Test
	void deveriaJogarExcecaoQuandoCadastrarAulaComProfessorComAulaAgendadaParaOMesmoHorario() {
		
		Turma turma = entidadeTurma();
		when(turmaService.buscarEntidadeAtiva(any())).thenReturn(turma);
		
		Professor professor = entidadeProfessor();
		when(professorService.buscarEntidadeAtiva(any())).thenReturn(professor);
		
		Disciplina disciplina = entidadeDisciplina();
		when(disciplinaService.buscarEntidadeAtiva(any())).thenReturn(disciplina);
		
		AulaDadosCadastro dados = new AulaDadosCadastro(LocalDate.of(2023, 1, 19), Ordem.PRIMEIRA_AULA, turma.getId(), professor.getId(), disciplina.getId());
		
		when(aulaRepository.findByIdProfessorAndPeriodoTurmaAndOrdemAula(any(), any(), any(), any())).thenReturn(Optional.of(Aula.builder().professor(professor).dia(LocalDate.of(2023, 1, 19)).ordem(Ordem.PRIMEIRA_AULA).build()));
		
		assertThrows(ResponseStatusException.class, () -> aulaService.cadastrarAula(dados));
		
		verify(aulaRepository, never()).save(any());
	}
	
	@Test
	void deveriaJogarExcecaoQuandoCadastrarAulaDeTurmaQueNaoPossuiADisciplinaInformadaNaGrade() {
		
		Turma turma = entidadeTurma();
		when(turmaService.buscarEntidadeAtiva(any())).thenReturn(turma);
		
		Professor professor = entidadeProfessor();
		when(professorService.buscarEntidadeAtiva(any())).thenReturn(professor);
		
		Disciplina disciplina = entidadeDisciplina();
		when(disciplinaService.buscarEntidadeAtiva(any())).thenReturn(disciplina);
		
		AulaDadosCadastro dados = new AulaDadosCadastro(LocalDate.of(2023, 1, 19), Ordem.PRIMEIRA_AULA, turma.getId(), professor.getId(), disciplina.getId());
		
		turma.setDisciplinas(new ArrayList<>() {{
			add(Disciplina.builder().id(2L).nome("Matemática").cargaHoraria(new BigDecimal("400.0")).ativo(true)
					.build());
		}});
		
		assertThrows(ResponseStatusException.class, () -> aulaService.cadastrarAula(dados));
		
		verify(aulaRepository, never()).save(any());
	}
	
	@Test
	void deveriaJogarExcecaoQuandoCadastrarAulaDeTurmaQueJaSupriuACargaHorariaDaDisciplinaInformada() {
		
		Turma turma = entidadeTurma();
		when(turmaService.buscarEntidadeAtiva(any())).thenReturn(turma);
		
		Professor professor = entidadeProfessor();
		when(professorService.buscarEntidadeAtiva(any())).thenReturn(professor);
		
		Disciplina disciplina = entidadeDisciplina();
		when(disciplinaService.buscarEntidadeAtiva(any())).thenReturn(disciplina);
		
		AulaDadosCadastro dados = new AulaDadosCadastro(LocalDate.of(2023, 1, 19), Ordem.PRIMEIRA_AULA, turma.getId(), professor.getId(), disciplina.getId());
		
		when(aulaRepository.totalDeAulasPorTurmaPorDisciplina(any(), any())).thenReturn(400);
		
		assertThrows(ResponseStatusException.class, () -> aulaService.cadastrarAula(dados));
		
		verify(aulaRepository, never()).save(any());
	}
	
	@Test
	void deveriaJogarExcecaoQuandoCadastrarAulaComProfessorNaoIndicadoParaLecionarADisciplinaInformada() {
		
		Turma turma = entidadeTurma();
		when(turmaService.buscarEntidadeAtiva(any())).thenReturn(turma);
		
		Professor professor = entidadeProfessor();
		when(professorService.buscarEntidadeAtiva(any())).thenReturn(professor);
		
		Disciplina disciplina = entidadeDisciplina();
		when(disciplinaService.buscarEntidadeAtiva(any())).thenReturn(disciplina);
		
		AulaDadosCadastro dados = new AulaDadosCadastro(LocalDate.of(2023, 1, 19), Ordem.PRIMEIRA_AULA, turma.getId(), professor.getId(), disciplina.getId());
		
		professor.setDisciplinas(new ArrayList<>() {{
			add(Disciplina.builder().id(2L).nome("Matemática").cargaHoraria(new BigDecimal("400.0")).ativo(true)
					.build());
		}});
		
		assertThrows(ResponseStatusException.class, () -> aulaService.cadastrarAula(dados));
		
		verify(aulaRepository, never()).save(any());
	}

	@Test
	void deletarUmaAula() {
		Aula aula = entidadeAula();
		when(aulaRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(aula));
		aulaService.deletarAula(1L);
		assertFalse(aula.getAtivo());
	}
	
/******************************************************************************************************************/
	/*Criação das entidades que precisarei para efetuar os testes na Entidade Aula*/
	
	private Turma entidadeTurma() {
		return Turma.builder().id(1L).codigo("M1").periodo(Periodo.MATUTINO).serie(Serie.PRIMEIRO_ANO).ativo(true)
				.alunos(new ArrayList<>()).aulas(new ArrayList<>())
				.disciplinas(new ArrayList<>() {{
					add(entidadeDisciplina());
				}}).build();
	}

	private Disciplina entidadeDisciplina() {
		return Disciplina.builder().id(1L).nome("Informática").cargaHoraria(new BigDecimal("400.0")).ativo(true)
				.build();
	}
	
	private Professor entidadeProfessor() { 
		return Professor.builder() 
				.id(1L).nome("Sara Henschel").matricula("262626").cpf("12312312344").telefone("47987876565")
				.email("sara@email.com").salario(new BigDecimal("2300.00")).formacao("Inglês")
				.endereco(Endereco.builder().logradouro("Rua São Paulo").numero(12).complemento("Bloco G")
						.bairro("Alameda").cidade("Blumenau").uf("SC").cep("22155449").build())
				.ativo(true)
				.disciplinas(new ArrayList<>() {{
					add(entidadeDisciplina());
				}}).build();
	}
	
	private Aula entidadeAula() {
		return Aula.builder().id(1L).dia(LocalDate.of(2023, 12, 12)).ordem(Ordem.PRIMEIRA_AULA).ativo(true)
				.disciplina(entidadeDisciplina()).professor(entidadeProfessor()).turma(entidadeTurma()).build();
	}
}
