package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.Optional;

public interface ITorneoRepository extends JpaRepository<Torneo,Long> {


    @NativeQuery(value = """
    SELECT TOP 1 * FROM TORNEOS T
    WHERE T.STATUS = 1
    ORDER BY T.FECHA_INICIO DESC
""")
    Optional<Torneo> findActualTorneo();

}
