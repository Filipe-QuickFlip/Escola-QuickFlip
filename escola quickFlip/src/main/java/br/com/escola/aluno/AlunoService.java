package br.com.escola.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.escola.email.EmailDetails;
import br.com.escola.email.EmailService;
import br.com.escola.turma.TurmaService;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository repository;
	
	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private EmailService emailService;
	
	public Page<AlunoDadosListagem> buscarTodos(Pageable paginacao) {
		return repository.findAllByAtivoTrue(paginacao).map(a -> new AlunoDadosListagem(a));
	}
	
	public AlunoDadosCompleto buscarPorId(Long id) {
		return new AlunoDadosCompleto(buscarEntidadeAtiva(id));
	}
	
	public Page<AlunoDadosListagem> buscarTodosPorNome(String nome, Pageable paginacao) {
		return repository.findAllByNomeContainsIgnoreCaseAndAtivoTrue(nome, paginacao).map(a -> new AlunoDadosListagem(a));
	}
	
	public AlunoDadosCompleto cadastarAluno(AlunoDadosCadastro dados) {
		if(repository.findByCpf(dados.cpf()).isPresent()){
			throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado!");
		}
		
		if(repository.findByMatricula(dados.matricula()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Matrícula já cadastrada!");
		}
		
		Aluno aluno = new Aluno(dados);
		repository.save(aluno);
		return new AlunoDadosCompleto(aluno);
	}
	
	public AlunoDadosCompleto atualizarAluno(AlunoDadosAtualizacao dados) {
		Aluno aluno = buscarEntidadeAtiva(dados.id());
		aluno.atualizar(dados);
		return new AlunoDadosCompleto(aluno);
	}
	
	public String matricularAluno(AlunoDadosMatriculaTurma dados) {
		Aluno aluno = buscarEntidadeAtiva(dados.id());
		aluno.matricularEmTurma(turmaService.buscarEntidadeAtiva(dados.idTurma()));
		return enviarEmailAluno(aluno);
	}
	
	private String enviarEmailAluno(Aluno aluno) {
		
		String titulo = "Bem Vindo à Escola QuickFlip";
		

		String mensagem = 
				"Caro (a) , " + aluno.getNome() + "\n\n" + 
				" Estamos muito contentes em comunicar a você que sua matrícula foi aceita! \n\n "
				+ " Você está matriculado no " + aluno.getTurma().getSerie() + ", da turma " + aluno.getTurma().getCodigo() + " com as atividades ocorrendo no período " + aluno.getTurma().getPeriodo() + ".\n\n" +
				" Vamos juntos em busca de nossos objetivos, agradecemos a confiança! \n\n"
				+ " Um cordial abraço, \n   Escola QuickFlip \n\n"
				;
		EmailDetails email = new EmailDetails(aluno.getEmail(), mensagem, titulo, null);
		
		return emailService.sendSimpleMail(email);
	}

	public AlunoDadosCompleto reativarAluno(Long id) {
		Aluno aluno = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado!"));
		
		if(aluno.getAtivo()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Aluno já ativo, favor informar id de um aluno desativado!");
		}
		
		aluno.ativar();
		return new AlunoDadosCompleto(aluno);
	}
	
	public void desativarAluno(Long id) {
		Aluno aluno = buscarEntidadeAtiva(id);
		aluno.desativar();
		aluno.setTurma(null);
	}
	
	public Aluno buscarEntidadeAtiva(Long id) {
		return repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno inativo ou inexistente!"));
	}
	
}
