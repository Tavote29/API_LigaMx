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

    @NativeQuery(value = """
            with partidos_torneo as (
            select
                p.ID_PARTIDO
            from
                partidos p
            where
                p.ID_TORNEO = :torneoId
            ),
            acont_jugador as (
            select
                a.ID_PARTIDO,
                a.ID_TIPO
            from
                ACONTECIMIENTOS a
            where
                a.ID_JUGADOR_PRIMARIO = :jugadorId
            )
            
            
            
            select
                SUM(CASE WHEN ta.DESCRIPCION_TIPO = 'TARJETA AMARILLA' THEN 1 ELSE 0 END) AS tarjetas_amarillas,
                SUM(CASE WHEN ta.DESCRIPCION_TIPO = 'TARJETA ROJA' THEN 1 ELSE 0 END) as tarjetas_rojas,
                SUM(CASE WHEN ta.DESCRIPCION_TIPO in ('TARJETA AMARILLA', 'TARJETA ROJA', 'FALTA') THEN 1 ELSE 0 END) as faltas_cometidas
            from
                acont_jugador aj
            inner join TIPOS_ACONTECIMIENTOS ta on
                aj.ID_TIPO = ta.ID_TIPO
            inner join partidos_torneo pt on
                aj.ID_PARTIDO = pt.ID_PARTIDO
            where
                ta.DESCRIPCION_TIPO in ('TARJETA AMARILLA', 'TARJETA ROJA', 'FALTA');
""")
    Object[] obtenerTarjetasJugadorPorTorneoId(@Param("jugadorId") Long jugadorId,@Param("torneoId") Long torneoId);
}
