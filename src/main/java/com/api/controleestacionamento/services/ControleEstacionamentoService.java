package com.api.controleestacionamento.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.controleestacionamento.models.ControleEstacionamentoModel;
import com.api.controleestacionamento.repositories.ControleEstacionamentoRepository;

import jakarta.transaction.Transactional;

@Service
public class ControleEstacionamentoService {

	final ControleEstacionamentoRepository controleEstacionamentoRepository;

	public ControleEstacionamentoService(ControleEstacionamentoRepository controleEstacionamentoRepository) {
		this.controleEstacionamentoRepository = controleEstacionamentoRepository;
	}

	@Transactional
	public ControleEstacionamentoModel save(ControleEstacionamentoModel controleEstacionamentoModel) {
		return controleEstacionamentoRepository.save(controleEstacionamentoModel);
	}

	public boolean existsByPlacaCarro(String placaCarro) {
		return controleEstacionamentoRepository.existsByPlacaCarro(placaCarro);
	}

	public boolean existsByNumeroVaga(String numeroVaga) {
		return controleEstacionamentoRepository.existsByNumeroVaga(numeroVaga);
	}

	public boolean existsByApartamentoEBloco(String apartamento, String bloco) {
		return controleEstacionamentoRepository.existsByApartamentoEBloco(apartamento, bloco);
	}

	public Page<ControleEstacionamentoModel> findAll(Pageable pageable) {
		return controleEstacionamentoRepository.findAll(pageable);
	}

	public Optional<ControleEstacionamentoModel> findById(UUID id) {
		return controleEstacionamentoRepository.findById(id);
	}

	@Transactional
	public void delete(ControleEstacionamentoModel controleEstacionamentoModel) {
		controleEstacionamentoRepository.delete(controleEstacionamentoModel);
	}
}
