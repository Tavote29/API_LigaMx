package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.dtos.projection.TarjetasResumenPorTorneo;
import com.fdf.liga_mx.models.dtos.projection.getTarjetasPorPartido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import com.fdf.liga_mx.models.entitys.Jugador;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


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
                                @Param("nacionalidad") Short nacionalidad,
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
    TarjetasResumenPorTorneo obtenerTarjetasJugadorPorTorneoId(Long jugadorId, Long torneoId);


    @NativeQuery(value = """

                    WITH acont_partido
             AS (SELECT a.id_partido,
                        a.id_jugador_primario,
                        a.id_tipo
                 FROM   acontecimientos a
                 WHERE  a.id_partido = :partidoId)
        SELECT ap.id_jugador_primario as id_jugador,
               Sum(CASE
                     WHEN ta.descripcion_tipo = 'TARJETA AMARILLA' THEN 1
                     ELSE 0
                   END) AS tarjetas_amarillas,
               Sum(CASE
                     WHEN ta.descripcion_tipo = 'TARJETA ROJA' THEN 1
                     ELSE 0
                   END) AS tarjetas_rojas
        FROM   acont_partido ap
               INNER JOIN tipos_acontecimientos ta
                       ON ap.id_tipo = ta.id_tipo
        WHERE  ta.descripcion_tipo IN ( 'TARJETA AMARILLA', 'TARJETA ROJA' )
        GROUP  BY ap.id_jugador_primario
            
""")
    List<getTarjetasPorPartido> obtenerTarjetasPorPartidoId(@Param("partidoId") String partidoId);


    @NativeQuery(value = """

            SELECT CASE
		WHEN EXISTS (
				SELECT 1
				FROM JUGADORES J
				WHERE J.ID_CLUB IN (
						P.ID_LOCAL
						,P.ID_VISITANTE
						)
					AND J.NUI_JUGADOR = :jugadorId
				)
			THEN 1
		ELSE 0
		END AS ExisteJugador
            FROM PARTIDOS P
            WHERE P.ID_PARTIDO = :partidoId;

            
""")
    int isMatchPlayer(@Param("partidoId") UUID partidoId, @Param("jugadorId") Long jugadorId);

}
