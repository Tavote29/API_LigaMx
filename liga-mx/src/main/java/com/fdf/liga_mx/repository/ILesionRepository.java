package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Lesion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ILesionRepository extends JpaRepository<Lesion, UUID> {
}
