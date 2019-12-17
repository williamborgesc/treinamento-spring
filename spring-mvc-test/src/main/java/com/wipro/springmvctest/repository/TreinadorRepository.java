package com.wipro.springmvctest.repository;

import org.springframework.data.repository.CrudRepository;

import com.wipro.springmvctest.entity.Treinador;

public interface TreinadorRepository extends CrudRepository<Treinador, Long> {

}
