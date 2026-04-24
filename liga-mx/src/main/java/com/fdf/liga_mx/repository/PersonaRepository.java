package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonaRepository extends JpaRepository<Persona,UUID> {

}
