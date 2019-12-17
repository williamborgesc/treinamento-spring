package com.wipro.springmvctest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.springmvctest.entity.Treinador;
import com.wipro.springmvctest.repository.TreinadorRepository;

@Service
public class TreinadorService {

	@Autowired
	private TreinadorRepository treinadorRepository;

	public Iterable<Treinador> obterTodos() {

		return treinadorRepository.findAll();

	}

	public Treinador criarTreinador(Treinador treinador) {

		return treinadorRepository.save(treinador);
	}

	public Treinador obterPorId(Long id) {

		Optional<Treinador> optionalTreinador = treinadorRepository.findById(id);

		return optionalTreinador.orElseThrow(() -> new RuntimeException("Treinador não existe vacilão :p"));

	}

	public void removerPorId(Long id) {
		
		treinadorRepository.deleteById(id);
	}

	public void atualizarTreinador(Treinador treinador) {
		
		treinadorRepository.save(treinador);
		
	}

}
