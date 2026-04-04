package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITorneoRepository extends JpaRepository<Torneo,Long> {
}
