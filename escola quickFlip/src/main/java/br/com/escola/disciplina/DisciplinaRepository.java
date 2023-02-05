package br.com.escola.disciplina;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{

	Optional<Disciplina> findByIdAndAtivoTrue(Long id);

	Page<Disciplina> findAllByAtivoTrue(Pageable paginacao);

	Optional<Disciplina> findByNome(String nome);

	Page<Disciplina> findAllByNomeContainsIgnoreCaseAndAtivoTrue(String nome, Pageable paginacao);

}
