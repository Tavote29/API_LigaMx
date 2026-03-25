package com.fdf.liga_mx.models.repositories;

import com.fdf.liga_mx.models.entitys.TiposAcontecimiento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITiposAcontecimientoRepository extends CrudRepository<TiposAcontecimiento, Short> {
}