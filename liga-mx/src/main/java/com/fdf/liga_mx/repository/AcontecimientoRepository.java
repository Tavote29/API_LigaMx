package com.fdf.liga_mx.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.fdf.liga_mx.models.entitys.Acontecimiento;

public interface AcontecimientoRepository extends JpaRepository<Acontecimiento, UUID> {

}
