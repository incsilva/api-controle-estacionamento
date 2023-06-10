package com.api.controleestacionamento.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.controleestacionamento.dtos.ControleEstacionamentoDto;
import com.api.controleestacionamento.models.ControleEstacionamentoModel;
import com.api.controleestacionamento.services.ControleEstacionamentoService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vaga-de-estacionamento")
public class ControleEstacionamentoController {

	final ControleEstacionamentoService controleEstacionamentoService;

	public ControleEstacionamentoController(ControleEstacionamentoService controleEstacionamentoService) {
		this.controleEstacionamentoService = controleEstacionamentoService;
	}

	@PostMapping
	public ResponseEntity<Object> saveVagaEstacionamento(
			@RequestBody @Valid ControleEstacionamentoDto controleEstacionamentoDto) {
		if (controleEstacionamentoService.existsByPlacaCarro(controleEstacionamentoDto.getPlacaCarro())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Esta placa já está em uso!");
		}
		if (controleEstacionamentoService.existsByNumeroVaga(controleEstacionamentoDto.getNumeroVaga())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Esta vaga já está em uso!");
		}

		if (controleEstacionamentoService.existsByApartamentoEBloco(controleEstacionamentoDto.getApartmento(),
				controleEstacionamentoDto.getBloco())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Conflito: Já existe uma vaga registrada para este apartamento/bloco!");
		}
		var controleEstacionamentoModel = new ControleEstacionamentoModel();
		BeanUtils.copyProperties(controleEstacionamentoDto, controleEstacionamentoModel);
		controleEstacionamentoModel.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(controleEstacionamentoService.save(controleEstacionamentoModel));
	}

	@GetMapping
	public ResponseEntity<Page<ControleEstacionamentoModel>> getAllVagasEstacionamento(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(controleEstacionamentoService.findAll(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUmaVaga(@PathVariable(value = "id") UUID id) {
		Optional<ControleEstacionamentoModel> controleEstacionamentoOptional = controleEstacionamentoService
				.findById(id);
		if (!controleEstacionamentoOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(controleEstacionamentoOptional.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteVagaEstacionamento(@PathVariable(value = "id") UUID id) {
		Optional<ControleEstacionamentoModel> controleEstacionamentoOptional = controleEstacionamentoService
				.findById(id);
		if (!controleEstacionamentoOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada.");
		}
		controleEstacionamentoService.delete(controleEstacionamentoOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Vaga de estacionamento deletada com sucesso.");
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateVagaEstacionamento(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid ControleEstacionamentoDto controleEstacionamentoDto) {
		Optional<ControleEstacionamentoModel> controleEstacionamentoModelOptional = controleEstacionamentoService
				.findById(id);
		if (!controleEstacionamentoModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada.");
		}
		var controleEstacionamentoModel = new ControleEstacionamentoModel();
		BeanUtils.copyProperties(controleEstacionamentoDto, controleEstacionamentoModel);
		controleEstacionamentoModel.setId(controleEstacionamentoModelOptional.get().getId());
		controleEstacionamentoModel.setDataRegistro(controleEstacionamentoModelOptional.get().getDataRegistro());
		return ResponseEntity.status(HttpStatus.OK)
				.body(controleEstacionamentoService.save(controleEstacionamentoModel));
	}

}
