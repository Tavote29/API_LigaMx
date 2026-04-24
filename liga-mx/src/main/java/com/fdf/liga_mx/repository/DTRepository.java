package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.DT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DTRepository extends JpaRepository<DT, Long> {

    String QUERY = """
            SELECT d FROM DT d
            INNER JOIN FETCH d.persona p
            INNER JOIN FETCH p.idNacionalidad n
            INNER JOIN FETCH d.club c
            """;
    String FILTERS = """
            WHERE (:nombre IS NULL OR p.nombre LIKE %:nombre%)
            AND (:nacionalidad IS NULL OR p.idNacionalidad = :nacionalidad)
            AND (:club IS NULL OR d.Club = :club)
            """;

    @NativeQuery(value = QUERY + FILTERS,countQuery = "SELECT COUNT(d) FROM DT d")
    Page<DT> searchDT(Pageable pageable,
                                @Param("nombre") String nombre,
                                @Param("nacionalidad") Integer nacionalidad,
                                @Param("club") Short club
    );

    Optional<DT> findByIdAndStatusIs(Long id, Short status);
}
