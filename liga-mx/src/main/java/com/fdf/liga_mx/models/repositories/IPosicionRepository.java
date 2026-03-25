package com.fdf.liga_mx.models.repositories;

import com.fdf.liga_mx.models.entitys.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPosicionRepository extends JpaRepository<Posicion, Short> {
}