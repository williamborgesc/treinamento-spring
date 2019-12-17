package com.wipro.springmvctest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.springmvctest.entity.Pokemon;
import com.wipro.springmvctest.service.PokemonService;

@RestController
@RequestMapping("/treinador/{treinadorId}/pokemon")
public class PokemonController {

	@Autowired
	private PokemonService pokemonService;

	@PostMapping
	public Pokemon adicionarPokemon(@PathVariable Long treinadorId, @RequestBody Pokemon pokemon) {
		
		return  pokemonService.adicionarPokemon(treinadorId, pokemon);
	}

}
