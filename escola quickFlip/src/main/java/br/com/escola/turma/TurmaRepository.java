package br.com.escola.turma;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long>{

	Page<Turma> findAll(Pageable paginacao);

	Page<Turma> findAllByAtivoFalse(Pageable paginacao);

	Turma findByIdAndAtivoFalse(Long id);
	
	Optional<Turma> findByIdAndAtivoTrue(Long id);

	Page<Turma> findAllByAtivoTrue(Pageable paginacao);

	Optional<Turma> findByCodigo(String codigo);

	Page<Turma> findAllByCodigoContainsIgnoreCaseAndAtivoTrue(String codigo, Pageable paginacao);
	
}
