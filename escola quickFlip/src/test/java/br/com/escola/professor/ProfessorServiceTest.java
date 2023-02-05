package br.com.escola.professor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import br.com.escola.endereco.Endereco;
import br.com.escola.endereco.EnderecoDadosAtualizacao;
import br.com.escola.endereco.EnderecoDadosCadastro;
import br.com.escola.professor.Professor;
import br.com.escola.professor.ProfessorDadosAtualizacao;
import br.com.escola.professor.ProfessorDadosCadastro;
import br.com.escola.professor.ProfessorRepository;
import br.com.escola.professor.ProfessorService;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {

	@InjectMocks
	private ProfessorService professorService;

	@Mock
	private ProfessorRepository professorRepository;

	@Test
	void deveriaJogarExcecaoQuandoNaoEncontrarProfessor() {
		when(professorRepository.findByIdAndAtivoTrue(any()))
				.thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

		assertThrows(ResponseStatusException.class, () -> professorService.buscarEntidadeAtiva(any()));
	}

	@Test
	void deveriaAtualizarProfessor() {

		Professor professores = entidadeProfessor();
		when(professorRepository.findByIdAndAtivoTrue(any())).thenReturn(Optional.of(professores));

		ProfessorDadosAtualizacao dadosAtualizacao = new ProfessorDadosAtualizacao(1L, "Debora Henschel",
				"debora@gmail.com", "47990908787", new BigDecimal(2500.00), "Educação Especial",
				new EnderecoDadosAtualizacao("Rua das Palmeiras", 789, "Bloco A", "Victor Konder", "Blumenau", "SC",
						"76543221"));

		professorService.atualizar(dadosAtualizacao);

		assertEquals(dadosAtualizacao.nome(), professores.getNome());
		assertEquals(dadosAtualizacao.email(), professores.getEmail());
		assertEquals(dadosAtualizacao.telefone(), professores.getTelefone());
		assertEquals(dadosAtualizacao.salario(), professores.getSalario());
		assertEquals(dadosAtualizacao.formacao(), professores.getFormacao());
		assertEquals(dadosAtualizacao.endereco().logradouro(), professores.getEndereco().getLogradouro());
		assertEquals(dadosAtualizacao.endereco().numero(), professores.getEndereco().getNumero());
		assertEquals(dadosAtualizacao.endereco().complemento(), professores.getEndereco().getComplemento());
		assertEquals(dadosAtualizacao.endereco().bairro(), professores.getEndereco().getBairro());
		assertEquals(dadosAtualizacao.endereco().cidade(), professores.getEndereco().getCidade());
		assertEquals(dadosAtualizacao.endereco().uf(), professores.getEndereco().getUf());
		assertEquals(dadosAtualizacao.endereco().cep(), professores.getEndereco().getCep());
	}

	@Test
	void deveriaJogarExcecaoDeConflitoQuandoCadastrarProfessorComCpfIguais() {
		Professor professor = entidadeProfessor();
		
		when(professorRepository.findByCpf(any())).thenReturn(Optional.of(professor));
		
		ProfessorDadosCadastro novoProfessor = new ProfessorDadosCadastro("Ana", "000001", "12312312344", "47987876565", "ana@gmail.com", new BigDecimal(2300.00), "Informática",
				new EnderecoDadosCadastro("R Sao Paulo", 560, "bloco F", "Victor Konder", "Blumenau", "SC", "56023116") );
		
		assertThrows(ResponseStatusException.class, () -> professorService.cadastrarProfessor(novoProfessor));
	}
	
	@Test
	void deveriaJogarExcecaoDeConflitoQuandoCadastrarProfessorComMatriculaIguais() {
		Professor professor = entidadeProfessor();
		
		when(professorRepository.findByMatricula(any())).thenReturn(Optional.of(professor));
		
		ProfessorDadosCadastro novoProfessor = new ProfessorDadosCadastro("Ana", "000001", "12312312344", "47987876565", "ana@gmail.com", new BigDecimal(2300.00), "Informática",
				new EnderecoDadosCadastro("R Sao Paulo", 560, "bloco F", "Victor Konder", "Blumenau", "SC", "56023116") );
		
		assertThrows(ResponseStatusException.class, () -> professorService.cadastrarProfessor(novoProfessor));
	}
	
	@Test
	void deveriaReativarUmProfessor() {
		Professor professor = entidadeProfessor();
		
		professor.desativar();
		when(professorRepository.findById(any())).thenReturn(Optional.of(professor));
		
		professorService.reativarProfessor(1L);
		
		assertTrue(professor.getAtivo());
	}
	
	
	@Test
	void deveriaDesativarUmProfessor() {
		Professor professor = entidadeProfessor();
		when(professorRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(professor));

		professorService.desativarProfessor(1L);

		assertFalse(professor.getAtivo());
	}

	private Professor entidadeProfessor() { // O método de buscar essa entidade será usado mais vezes durante o teste
		return Professor.builder() // Utiliza o builder para construir uma entidade que não dará problema caso não
									// insira todas a informações
				.id(1L).nome("Sara Henschel").matricula("262626").cpf("12312312344").telefone("47987876565")
				.email("sara@email.com").salario(new BigDecimal("2300.00")).formacao("Inglês")
				.endereco(Endereco.builder().logradouro("Rua São Paulo").numero(12).complemento("Bloco G")
						.bairro("Alameda").cidade("Blumenau").uf("SC").cep("22155449").build())
				.ativo(true).build();
	}

}
