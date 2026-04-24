package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Estadio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

public interface EstadioRepository extends JpaRepository<Estadio,Short> {

    String QUERY = "SELECT e FROM ESTADIO e ";

    String FILTERS = """
            WHERE (:ciudadId IS NULL OR e.id_ciudad = :ciudadId)
            AND (:estadoId IS NULL OR e.id_estado = :estadoId)
            AND (:nombre IS NULL OR e.nombre_estadio LIKE %:nombre%)
            AND (:minCapacity IS NULL OR e.capacidad >= :minCapacity)
            AND (:maxCapacity IS NULL OR e.capacidad <= :maxCapacity)
            """;

    @NativeQuery(value = QUERY + FILTERS, countQuery = "SELECT COUNT(e) FROM ESTADIO e")
    Page<Estadio> searchEstadio(Pageable pageable,
                                @Param("ciudadId") Short ciudadId,
                                @Param("estadoId") Short estadoId,
                                @Param("nombre") String nombre,
                                @Param("minCapacity") Integer minCapacity,
                                @Param("maxCapacity") Integer maxCapacity);
}
