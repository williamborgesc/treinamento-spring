package com.wipro.springmvctest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.springmvctest.entity.Treinador;
import com.wipro.springmvctest.service.TreinadorService;

@RestController
@RequestMapping("/treinador")
public class TreinadorController {

	@Autowired
	private TreinadorService treinadorService;

	@GetMapping
	public Iterable<Treinador> obterTodos() {

		return treinadorService.obterTodos();
	}
	
	@GetMapping("/{id}")
	public Treinador obterPorId(@PathVariable Long id) {
		
		return treinadorService.obterPorId(id);
	}

	@PostMapping
	public Treinador criarTreinador(@RequestBody Treinador treinador) {

		return treinadorService.criarTreinador(treinador);
	}
	
	@DeleteMapping("/{id}")
	public void removerTreinador(@PathVariable Long id) {
		
		treinadorService.removerPorId(id);
	}
	
	@PutMapping
	public void atualizarTreinador(@RequestBody Treinador treinador) {
		
		treinadorService.atualizarTreinador(treinador);
	}
	
	
}
