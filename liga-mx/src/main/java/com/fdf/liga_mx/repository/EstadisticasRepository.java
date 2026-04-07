package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.dtos.projection.getMarcadorPartido;
import com.fdf.liga_mx.models.dtos.projection.getTablaCociente;
import com.fdf.liga_mx.models.dtos.projection.getTablaGoleoIndividual;
import com.fdf.liga_mx.models.dtos.projection.getTablaPosiciones;
import com.fdf.liga_mx.models.entitys.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadisticasRepository extends JpaRepository<Torneo, Long> {
    String TABLA_POSICIONES = """
                SELECT
                    C.NOMBRE_CLUB AS CLUB, 
                    SUM(CASE 
                        WHEN P.ID_STATUS = 5 THEN 1
                        ELSE 0
                    END) AS PJ,
                    SUM(CASE 
                        WHEN P.ID_STATUS = 5
                        AND (
                            (P.ID_LOCAL = C.ID_CLUB AND P.GOLES_LOCAL > P.GOLES_VISITANTE)
                            OR
                            (P.ID_VISITANTE = C.ID_CLUB AND P.GOLES_VISITANTE > P.GOLES_LOCAL)
                        )
                        THEN 1 ELSE 0
                    END) AS PG,
                    SUM(CASE
                        WHEN P.ID_STATUS = 5
                        AND P.GOLES_LOCAL = P.GOLES_VISITANTE
                        THEN 1 ELSE 0
                    END) AS PE,
                    SUM(CASE 
                        WHEN P.ID_STATUS = 5
                        AND (
                            (P.ID_LOCAL = C.ID_CLUB AND P.GOLES_LOCAL < P.GOLES_VISITANTE)
                            OR
                            (P.ID_VISITANTE = C.ID_CLUB AND P.GOLES_VISITANTE < P.GOLES_LOCAL)
                        )
                        THEN 1 ELSE 0
                    END) AS PP,
                    SUM(CASE
                        WHEN P.ID_LOCAL = C.ID_CLUB THEN ISNULL(P.GOLES_LOCAL,0)
                        WHEN P.ID_VISITANTE = C.ID_CLUB THEN ISNULL(P.GOLES_VISITANTE,0)
                        ELSE 0
                    END) AS GF,
                    SUM(CASE
                        WHEN P.ID_LOCAL = C.ID_CLUB THEN ISNULL(P.GOLES_VISITANTE,0)
                        WHEN P.ID_VISITANTE = C.ID_CLUB THEN ISNULL(P.GOLES_LOCAL,0)
                        ELSE 0
                    END) AS GC,
                    SUM(CASE
                        WHEN P.ID_LOCAL = C.ID_CLUB THEN CAST(ISNULL(P.GOLES_LOCAL,0)AS INT) - CAST(ISNULL(P.GOLES_VISITANTE,0)AS INT)
                        WHEN P.ID_VISITANTE = C.ID_CLUB THEN CAST(ISNULL(P.GOLES_VISITANTE,0)AS INT) - CAST(ISNULL(P.GOLES_LOCAL,0)AS INT)
                        ELSE 0
                    END) AS DG,
                    SUM(CASE
                        WHEN P.ID_STATUS = 5 AND (
                            (P.ID_LOCAL = C.ID_CLUB AND P.GOLES_LOCAL > P.GOLES_VISITANTE)
                            OR
                            (P.ID_VISITANTE = C.ID_CLUB AND P.GOLES_VISITANTE > P.GOLES_LOCAL)
                        ) THEN 3
                        WHEN P.ID_STATUS = 5 AND P.GOLES_LOCAL = P.GOLES_VISITANTE 
                        THEN 1
                        ELSE 0
                    END)
                     AS PUNTOS
                FROM CLUBES C
                LEFT JOIN PARTIDOS P 
                    ON C.ID_CLUB IN (P.ID_LOCAL, P.ID_VISITANTE)
                    AND P.ID_TORNEO = :id
                LEFT JOIN TORNEOS T 
                    ON T.ID_TORNEO = P.ID_TORNEO
                GROUP BY C.NOMBRE_CLUB
                ORDER BY PUNTOS DESC, DG DESC, GF DESC
            """;

    String TABLA_GOLEO_INDIVIDUAL = """
            SELECT
            	P.NOMBRE AS NOMBRE,
            	J.DORSAL AS DORSAL,
            	C.NOMBRE_CLUB AS CLUB, 
            	N.NOMBRE_NACIONALIDAD AS NACIONALIDAD, 
            	COUNT(*) AS GOLES
            FROM
            	JUGADORES AS J
            INNER JOIN ACONTECIMIENTOS AS A ON J.NUI_JUGADOR = A.ID_JUGADOR_PRIMARIO
            AND A.ID_TIPO = 1
            LEFT JOIN CLUBES AS C ON J.ID_CLUB = C.ID_CLUB
            INNER JOIN PERSONAS AS P ON J.ID_PERSONA = P.ID_PERSONA
            INNER JOIN NACIONALIDADES AS N ON P.ID_NACIONALIDAD = N.ID_NACIONALIDAD 
            INNER JOIN PARTIDOS AS PA ON A.ID_PARTIDO  = PA.ID_PARTIDO
            INNER JOIN TORNEOS AS T ON PA.ID_TORNEO = T.ID_TORNEO 
            AND T.ID_TORNEO = :id
            GROUP BY P.NOMBRE, J.DORSAL, C.NOMBRE_CLUB, N.NOMBRE_NACIONALIDAD 
            ORDER BY GOLES DESC
            """;

    String TABLA_COCIENTE = """
            SELECT 
            	C.NOMBRE_CLUB AS CLUB,
            	SUM(CASE
            		WHEN P.ID_STATUS = 5
            		THEN 1
            	END
            	) AS TPJ,
            	SUM(CASE
                    WHEN P.ID_STATUS = 5 AND (
                        (P.ID_LOCAL = C.ID_CLUB AND P.GOLES_LOCAL > P.GOLES_VISITANTE)
                        OR
                        (P.ID_VISITANTE = C.ID_CLUB AND P.GOLES_VISITANTE > P.GOLES_LOCAL)
                    ) THEN 3
                    WHEN P.ID_STATUS = 5 AND P.GOLES_LOCAL = P.GOLES_VISITANTE 
                    THEN 1
                    ELSE 0
            	END
            	) AS PUNTOS,
            	ROUND(
            	SUM(CASE
                    WHEN P.ID_STATUS = 5 AND (
                        (P.ID_LOCAL = C.ID_CLUB AND P.GOLES_LOCAL > P.GOLES_VISITANTE)
                        OR
                        (P.ID_VISITANTE = C.ID_CLUB AND P.GOLES_VISITANTE > P.GOLES_LOCAL)
                    ) THEN 3
                    WHEN P.ID_STATUS = 5 AND P.GOLES_LOCAL = P.GOLES_VISITANTE 
                    THEN 1
                    ELSE 0
            	END
            	) * 1.0  /
            	NULLIF(SUM(CASE
            		WHEN P.ID_STATUS = 5
            		THEN 1
            	END), 0), 4) AS COCIENTE
            FROM
            	CLUBES AS C
            INNER JOIN PARTIDOS AS P ON
            	C.ID_CLUB IN (P.ID_LOCAL, P.ID_VISITANTE)
            INNER JOIN TORNEOS AS T ON
            	P.ID_TORNEO = T.ID_TORNEO
            WHERE
            	T.ID_TORNEO IN(
            SELECT TOP 6 T.ID_TORNEO 
            FROM TORNEOS 
            ORDER BY TORNEOS.FECHA_FIN DESC)
            GROUP BY
            	C.NOMBRE_CLUB
            ORDER BY
            	COCIENTE DESC
            """;

    String TABLA_OFENSIVA = """
                SELECT
                    C.NOMBRE_CLUB AS CLUB, 
                    SUM(CASE
                        WHEN P.ID_LOCAL = C.ID_CLUB THEN ISNULL(P.GOLES_LOCAL,0)
                        WHEN P.ID_VISITANTE = C.ID_CLUB THEN ISNULL(P.GOLES_VISITANTE,0)
                        ELSE 0
                    END) AS GOLES,
                    C.ID_CLUB AS IDCLUB
                FROM CLUBES C
                LEFT JOIN PARTIDOS P 
                    ON C.ID_CLUB IN (P.ID_LOCAL, P.ID_VISITANTE)
                    AND P.ID_TORNEO = :id
                LEFT JOIN TORNEOS T 
                    ON T.ID_TORNEO = P.ID_TORNEO
                GROUP BY C.NOMBRE_CLUB, C.ID_CLUB
                ORDER BY GOLES DESC
            """;

    String TABLA_DEFENSIVA = """
                         SELECT
                             C.NOMBRE_CLUB AS CLUB,  
                             SUM(CASE
                                 WHEN P.ID_LOCAL = C.ID_CLUB THEN ISNULL(P.GOLES_VISITANTE,0)
                                 WHEN P.ID_VISITANTE = C.ID_CLUB THEN ISNULL(P.GOLES_LOCAL,0)
                                 ELSE 0
                             END) AS GOLES,
                             C.ID_CLUB AS IDCLUB
                         FROM CLUBES C
                         LEFT JOIN PARTIDOS P 
                             ON C.ID_CLUB IN (P.ID_LOCAL, P.ID_VISITANTE)
                             AND P.ID_TORNEO = :id
                         LEFT JOIN TORNEOS T 
                             ON T.ID_TORNEO = P.ID_TORNEO
                         GROUP BY C.NOMBRE_CLUB, C.ID_CLUB
                         ORDER BY GOLES ASC
            
            """;
    @NativeQuery(value = TABLA_POSICIONES)
    List<getTablaPosiciones> obtenerTablaPosiciones(@Param("id") Long id);

    @NativeQuery(value = TABLA_GOLEO_INDIVIDUAL)
    List<getTablaGoleoIndividual> obtenerTablaGoleo(@Param("id") Long id);

    @NativeQuery(value = TABLA_COCIENTE)
    List<getTablaCociente> obtenerTablaCociente();

    @NativeQuery(value = TABLA_OFENSIVA)
    List<getMarcadorPartido> obtenerTablaOfensiva(@Param("id") Long id);

    @NativeQuery(value = TABLA_DEFENSIVA)
    List<getMarcadorPartido> obtenerTablaDefensiva(@Param("id") Long id);
}
