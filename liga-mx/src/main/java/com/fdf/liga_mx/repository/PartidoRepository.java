package com.fdf.liga_mx.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.fdf.liga_mx.models.entitys.Partido;

public interface PartidoRepository extends CrudRepository<Partido,UUID> {

}
