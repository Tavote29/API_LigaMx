package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Acontecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AcontecimientoRepository extends JpaRepository<Acontecimiento, UUID> {

}
