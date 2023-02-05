package br.com.escola.turma;

import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TurmaDadosCadastro(

		@NotBlank
		@Pattern(regexp = "\\w{2}")
		String codigo, 
		@NotNull
		Periodo periodo, 
		@NotNull
		Serie serie) {

	public TurmaDadosCadastro(Optional<Turma> obj) {
		this(obj.get().getCodigo(), obj.get().getPeriodo(), obj.get().getSerie());

	}
	
	
	

}
