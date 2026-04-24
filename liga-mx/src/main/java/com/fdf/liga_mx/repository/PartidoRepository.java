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


    @NativeQuery(value = """
WITH ACONTECIMIENTOS_PARTIDO
AS (
	SELECT A.ID_ACONTECIMIENTO
		,A.ID_PARTIDO
		,A.ID_TIPO
		,A.ID_JUGADOR_PRIMARIO
		,A.ID_JUGADOR_SECUNDARIO
	FROM ACONTECIMIENTOS A
	WHERE A.ID_PARTIDO = :uuid
		AND A.ID_TIPO = 2
	)
	,JUGADORES_PARTIDO
AS (
	SELECT J.NUI_JUGADOR
		,J.ID_CLUB
	FROM PARTIDOS P
	LEFT JOIN JUGADORES J ON J.ID_CLUB IN (
			P.ID_LOCAL
			,P.ID_VISITANTE
			)
	WHERE P.ID_PARTIDO = :uuid
	)
SELECT J.ID_CLUB AS id_club
	,COUNT(A.ID_JUGADOR_PRIMARIO) AS total_cambios
	,(
		SELECT A_det.ID_JUGADOR_PRIMARIO AS jugador_in
			,A_det.ID_JUGADOR_SECUNDARIO AS jugador_out
		FROM ACONTECIMIENTOS_PARTIDO A_det
		LEFT JOIN JUGADORES_PARTIDO J_det ON J_det.NUI_JUGADOR = A_det.ID_JUGADOR_PRIMARIO
		WHERE J_det.ID_CLUB = J.ID_CLUB
		FOR JSON PATH
		) AS detalles_cambios
FROM ACONTECIMIENTOS_PARTIDO A
LEFT JOIN JUGADORES_PARTIDO J ON J.NUI_JUGADOR = A.ID_JUGADOR_PRIMARIO
GROUP BY J.ID_CLUB
FOR JSON PATH;

""")
     String obtenerResumenCambios(@Param("uuid") UUID uuid);
}
