package com.fdf.liga_mx.repository;

import java.util.List;
import java.util.UUID;

import com.fdf.liga_mx.models.dtos.projection.getMarcadorPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import com.fdf.liga_mx.models.entitys.Partido;
import org.springframework.data.repository.query.Param;

public interface PartidoRepository extends JpaRepository<Partido,UUID> {

    String marcador = """
                SELECT
                    C.NOMBRE_CLUB AS club, 
                    SUM(
                    	CASE 
                    		WHEN A.ID_TIPO = 9 
                    		AND J.ID_CLUB <> C.ID_CLUB 
                    		THEN 1 
            
                    		WHEN A.ID_TIPO = 1
                    		AND J.ID_CLUB = C.ID_CLUB
                    		THEN 1 
            
                    		ELSE 0	 
                    	END
                    ) AS goles,
                    C.ID_CLUB AS idClub
                FROM PARTIDOS P 
                INNER JOIN CLUBES C 
                    ON C.ID_CLUB IN (P.ID_LOCAL, P.ID_VISITANTE)
                LEFT JOIN JUGADORES J 
                    ON J.ID_CLUB IN (P.ID_LOCAL, P.ID_VISITANTE)
                LEFT JOIN ACONTECIMIENTOS A 
                    ON A.ID_JUGADOR_PRIMARIO = J.NUI_JUGADOR
                    AND A.ID_PARTIDO = P.ID_PARTIDO
                    AND A.ID_TIPO IN (1,9)
                WHERE P.ID_PARTIDO = :uuid
                GROUP BY C.NOMBRE_CLUB,C.ID_CLUB;
            """;

    @NativeQuery(value = marcador)
    List<getMarcadorPartido> obtenerMarcador(@Param("uuid") UUID uuid);



}
