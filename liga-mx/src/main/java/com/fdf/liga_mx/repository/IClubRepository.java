package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IClubRepository extends JpaRepository<Club, Short> {

    List<Club> findByIdCiudadId(Short ciudadId);

    List<Club> findByIdEstadoId(Short estadoId);

    Optional<Club> findByIdAndStatusIs(Short id, Short status);


}
