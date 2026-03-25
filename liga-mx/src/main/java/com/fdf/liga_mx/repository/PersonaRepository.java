package com.fdf.liga_mx.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.fdf.liga_mx.models.entitys.Persona;

public interface PersonaRepository extends CrudRepository<Persona,UUID>{

}
