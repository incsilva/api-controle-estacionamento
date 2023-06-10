package com.api.controleestacionamento.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ControleEstacionamentoDto {

	@NotBlank
	private String numeroVaga;
	@NotBlank
	@Size(max = 7)
	private String placaCarro;
	@NotBlank
	private String marcaCarro;
	@NotBlank
	private String modeloCarro;
	@NotBlank
	private String corCarro;
	@NotBlank
	private String nomeResponsivo;
	@NotBlank
	private String apartmento;
	@NotBlank
	private String bloco;
}
