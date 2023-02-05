package br.com.escola.aluno;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	
	Page<Aluno> findAllByAtivoTrue(Pageable paginacao);

	Page<Aluno> findAllByNomeContainsIgnoreCaseAndAtivoTrue(String nome, Pageable paginacao);
	
	Optional<Aluno> findByIdAndAtivoTrue(Long id);

	Optional<Aluno> findByCpf(String cpf);

	Optional<Aluno> findByMatricula(String matricula);
	
	
	
}
