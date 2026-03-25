package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICiudadRepository extends JpaRepository<Ciudad, Short> {
}
