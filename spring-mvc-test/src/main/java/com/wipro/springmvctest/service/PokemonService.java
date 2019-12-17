package com.wipro.springmvctest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.wipro.springmvctest.entity.Pokemon;
import com.wipro.springmvctest.entity.Treinador;

@Service
public class PokemonService {
	
	@Autowired
	private TreinadorService treinadorService;
	
	@Autowired
	private RestTemplate restTemplate;

	private final String URL = "https://pokeapi.co/api/v2/pokemon/";

	public Pokemon adicionarPokemon(Long treinadorId, Pokemon pokemon) {
		
		Treinador treinador = treinadorService.obterPorId(treinadorId);
		
		Pokemon p = null;
		
		if (!StringUtils.isEmpty(pokemon.getId())) {
			
			p = restTemplate.getForObject(URL + pokemon.getId(), Pokemon.class);
	
		} else if (!StringUtils.isEmpty(pokemon.getNome())) {
	
			p = restTemplate.getForObject(URL + pokemon.getNome(), Pokemon.class);
	
		}
		
		treinador.getPokemons().add(p);
		
		treinadorService.atualizarTreinador(treinador);
		
		return pokemon;
	}

}
