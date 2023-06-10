package com.api.controleestacionamento.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.controleestacionamento.models.ControleEstacionamentoModel;

public interface ControleEstacionamentoRepository extends JpaRepository<ControleEstacionamentoModel, UUID> {

	boolean existsByPlacaCarro(String placaCarro);
    boolean existsByNumeroVaga(String numeroVaga);
	boolean existsByApartamentoEBloco(String apartamento, String bloco);
}
