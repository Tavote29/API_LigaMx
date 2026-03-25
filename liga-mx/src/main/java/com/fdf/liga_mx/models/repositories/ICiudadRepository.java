package com.fdf.liga_mx.models.repositories;

import com.fdf.liga_mx.models.entitys.Ciudad;
import org.springframework.data.repository.CrudRepository;

public interface ICiudadRepository extends CrudRepository<Ciudad,Integer> {
}
