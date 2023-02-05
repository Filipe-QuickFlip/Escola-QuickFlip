package br.com.escola.aula;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.escola.turma.Periodo;


@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
 
	Page<Aula> findAulaByProfessorIdAndAtivoTrue(Long id, Pageable paginacao);
 
	Page<Aula> findAulaByTurmaIdAndAtivoTrue(Long id, Pageable paginacao);
 
	Page<Aula> findAulaByDisciplinaIdAndAtivoTrue(Long id, Pageable paginacao);
	
	@Query("SELECT a FROM Aula a WHERE a.turma.periodo = 'MATUTINO' and a.ativo = true")
	Page<Aula> findAulaByTurmaPeriodoMatutino(Pageable paginacao);
	
	@Query("SELECT a FROM Aula a WHERE a.turma.periodo = 'VESPERTINO'  and a.ativo = true")
	Page<Aula> findAulaByTurmaPeriodoVespertino(Pageable paginacao);
	
	@Query("SELECT a FROM Aula a WHERE a.turma.periodo = 'NOTURNO'  and a.ativo = true")
	Page<Aula> findAulaByTurmaPeriodoNoturno(Pageable paginacao);
 
	Optional<Aula> findById(Long id);

	Page<Aula> findByAtivoTrue(Pageable paginacao);

	Page<Aula> findByAtivoFalse(Pageable paginacao);

	Optional<Aula> findByIdAndAtivoTrue(Long id);

	@Query(value = "select a.* from aula a inner join turma t on a.id_turma = t.id where t.id = :id and a.ordem = :ordem and a.ativo = true and a.dia = :data", nativeQuery = true)
	Optional<Aula> findByIdTurmaAndOrdemAula(Long id, String ordem, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate data);

	@Query(value = "select a.* from aula a inner join professor p on a.id_professor = p.id inner join turma t on a.id_turma = t.id where p.id = :id and t.periodo = :periodo and a.ordem = :ordem and a.ativo = true and a.dia = :data", nativeQuery = true)
	Optional<Aula> findByIdProfessorAndPeriodoTurmaAndOrdemAula(Long id, String periodo, String ordem, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate data);
	
	@Query(value = "select count(a.id) from aula a where a.id_turma = :idTurma and a.id_disciplina = :idDisciplina", nativeQuery = true)
	Integer totalDeAulasPorTurmaPorDisciplina(Long idTurma, Long idDisciplina);

	Page<Aula> findByTurmaPeriodoAndAtivoTrue(Periodo periodo, Pageable paginacao);
}
