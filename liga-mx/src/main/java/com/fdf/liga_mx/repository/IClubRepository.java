package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.entitys.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface IClubRepository extends JpaRepository<Club, Short> {

    List<Club> findByIdCiudadId(Short ciudadId);

    List<Club> findByIdEstadoId(Short estadoId);

    
}
