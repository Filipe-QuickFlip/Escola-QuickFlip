package br.com.escola.aula;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AulaDadosListagem(Long id, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dia, Ordem ordem, String disciplina ) {
		
	public AulaDadosListagem(Aula aula) {
		this(aula.getId(), aula.getDia(), aula.getOrdem(), aula.getDisciplina().getNome());
	}
}
