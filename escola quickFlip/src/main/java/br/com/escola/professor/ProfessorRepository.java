package br.com.escola.professor;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

	Page<Professor> findAllByAtivoTrue(Pageable paginacao);

	Page<Professor> findAllByNomeContainsIgnoreCaseAndAtivoTrue(String nome, Pageable paginacao);

	Page<Professor> findAllByFormacaoContainsIgnoreCaseAndAtivoTrue(String formacao, Pageable paginacao);
	
	Optional<Professor> findByIdAndAtivoTrue(Long id);

	Optional<Professor> findByCpf(String cpf);

	Optional<Professor> findByMatricula(String matricula);
}
