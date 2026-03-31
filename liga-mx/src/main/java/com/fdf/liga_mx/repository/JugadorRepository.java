package com.fdf.liga_mx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import com.fdf.liga_mx.models.entitys.Jugador;
import org.springframework.data.repository.query.Param;


public interface JugadorRepository extends JpaRepository<Jugador,Long> {

    String QUERY = """
            SELECT j FROM Jugador j
            INNER JOIN FETCH j.idPersona p
            INNER JOIN FETCH p.idNacionalidad n
            INNER JOIN FETCH j.idClub c
            """;
    String FILTERS = """
            WHERE (:nombre IS NULL OR p.nombre LIKE %:nombre%)
            AND (:nacionalidad IS NULL OR p.idNacionalidad = :nacionalidad)
            AND (:clubId IS NULL OR j.idClub = :clubId)
            """;

    @NativeQuery(value = QUERY + FILTERS,countQuery = "SELECT COUNT(j) FROM Jugador j")
    Page<Jugador> searchJugador(Pageable pageable,
                                @Param("nombre") String nombre,
                                @Param("nacionalidad") Integer nacionalidad,
                                @Param("clubId") Short clubId
    );
}
