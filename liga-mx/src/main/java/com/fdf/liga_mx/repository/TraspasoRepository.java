package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Traspaso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TraspasoRepository extends JpaRepository<Traspaso, UUID> {
}
