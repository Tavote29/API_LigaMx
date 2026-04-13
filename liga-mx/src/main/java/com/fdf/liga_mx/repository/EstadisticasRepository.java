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
            WITH Cambios AS (
                SELECT 
                    A.ID_PARTIDO,
                    A.ID_JUGADOR_PRIMARIO AS ID_JUGADOR_SALE,
                    A.ID_JUGADOR_SECUNDARIO AS ID_JUGADOR_ENTRA,
                    CASE 
                        WHEN CHARINDEX('+', A.MINUTO) > 0 THEN 
                            TRY_CAST(LEFT(A.MINUTO, CHARINDEX('+', A.MINUTO) - 1) AS INT) +
                            TRY_CAST(RIGHT(A.MINUTO, LEN(A.MINUTO) - CHARINDEX('+', A.MINUTO)) AS INT)
                        ELSE 
                            TRY_CAST(A.MINUTO AS INT)
                    END AS MINUTO
                FROM ACONTECIMIENTOS A
                WHERE A.ID_TIPO = 2
            )
            , JugadoresPartido AS (
                SELECT DISTINCT
                    A.ID_PARTIDO,
                    A.ID_JUGADOR_PRIMARIO AS ID_JUGADOR
                FROM ACONTECIMIENTOS A
            
                UNION
            
                SELECT DISTINCT
                    C.ID_PARTIDO,
                    C.ID_JUGADOR_ENTRA
                FROM Cambios C
            )
            , EntradasSalidas AS (
                SELECT 
                    JP.ID_PARTIDO,
                    JP.ID_JUGADOR,
            
                    ISNULL(
                        (SELECT MIN(C.MINUTO) 
                         FROM Cambios C 
                         WHERE C.ID_PARTIDO = JP.ID_PARTIDO 
                         AND C.ID_JUGADOR_ENTRA = JP.ID_JUGADOR),
                        0
                    ) AS MINUTO_ENTRADA,
            
                    ISNULL(
                        (SELECT MIN(C.MINUTO) 
                         FROM Cambios C  
                         WHERE C.ID_PARTIDO = JP.ID_PARTIDO 
                         AND C.ID_JUGADOR_SALE = JP.ID_JUGADOR),
                        90
                    ) AS MINUTO_SALIDA
            
                FROM JugadoresPartido JP
            )
            , MinutosPorJugador AS (
                SELECT 
                    E.ID_JUGADOR,
                    SUM(E.MINUTO_SALIDA - E.MINUTO_ENTRADA) AS MINUTOS_TOTALES
                FROM EntradasSalidas E
                INNER JOIN PARTIDOS P
                    ON E.ID_PARTIDO = P.ID_PARTIDO
                    AND P.ID_STATUS = 5
                WHERE P.ID_TORNEO = :id
                GROUP BY E.ID_JUGADOR
            )
            
            SELECT
                P.NOMBRE AS NOMBRE,
                MAX(J.DORSAL) AS DORSAL,
                MAX(C.NOMBRE_CLUB) AS CLUB, 
                MAX(N.NOMBRE_NACIONALIDAD) AS NACIONALIDAD, 
                COUNT(*) AS GOLES,
                ISNULL(M.MINUTOS_TOTALES, 0) AS MINUTOS_TOTALES
            FROM JUGADORES AS J
            INNER JOIN ACONTECIMIENTOS AS A 
                ON J.NUI_JUGADOR = A.ID_JUGADOR_PRIMARIO
                AND A.ID_TIPO = 1
            LEFT JOIN CLUBES AS C 
                ON J.ID_CLUB = C.ID_CLUB
            INNER JOIN PERSONAS AS P 
                ON J.ID_PERSONA = P.ID_PERSONA
            INNER JOIN NACIONALIDADES AS N 
                ON P.ID_NACIONALIDAD = N.ID_NACIONALIDAD 
            INNER JOIN PARTIDOS AS PA 
                ON A.ID_PARTIDO = PA.ID_PARTIDO
                AND PA.ID_STATUS = 5
            INNER JOIN TORNEOS AS T 
                ON PA.ID_TORNEO = T.ID_TORNEO 
                AND T.ID_TORNEO = :id
            LEFT JOIN MinutosPorJugador M
                ON J.NUI_JUGADOR = M.ID_JUGADOR
            GROUP BY 
                P.NOMBRE,
                M.MINUTOS_TOTALES
            ORDER BY GOLES DESC;
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
