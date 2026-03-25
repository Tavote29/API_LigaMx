package com.fdf.liga_mx.models.repositories;

import com.fdf.liga_mx.models.entitys.CategoriaArbitro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface ICategoriaArbitroRepository extends JpaRepository<CategoriaArbitro, Short> {
}
