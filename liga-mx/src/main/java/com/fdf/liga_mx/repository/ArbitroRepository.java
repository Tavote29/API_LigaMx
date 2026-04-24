package com.fdf.liga_mx.repository;

import com.fdf.liga_mx.models.entitys.Arbitro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

public interface ArbitroRepository extends JpaRepository<Arbitro,Long>{
    String QUERY = """
            SELECT a FROM Arbitro a
            INNER JOIN FETCH a.persona p
            INNER JOIN FETCH p.idNacionalidad n
            INNER JOIN FETCH a.idCategoriaArbitro c
            """;
    String FILTERS = """
            WHERE (:nombre IS NULL OR p.nombre LIKE %:nombre%)
            AND (:nacionalidad IS NULL OR p.idNacionalidad = :nacionalidad)
            AND (:categoria IS NULL OR a.idCategoriaArbitro = :categoria)
            """;

    @NativeQuery(value = QUERY + FILTERS,countQuery = "SELECT COUNT(a) FROM Arbitro a")
    Page<Arbitro> searchArbitro(Pageable pageable,
                      @Param("nombre") String nombre,
                      @Param("nacionalidad") Integer nacionalidad,
                      @Param("categoria") Short categoria
    );
}
