package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IClubRepository extends JpaRepository<Club, Short> {

    List<Club> findByIdCiudad(Short ciudad);

    List<Club> findByIdEstado(Short estado);

}
