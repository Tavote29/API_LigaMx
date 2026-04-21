package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.response.*;
import com.fdf.liga_mx.models.entitys.*;

import java.util.List;

public interface ICatalogosService {

    List<CategoriaArbitro> findAllCategoriasArbitrosEntity();

    List<Nacionalidad> findAllNacionalidadesEntity();

    List<Posicion> findAllPosicionesEntity();

    List<Status> findAllStatusesEntity();

    List<TiposAcontecimiento> findAllTiposAcontecimientosEntity();

    List<Estado> findAllEstado();

    List<Ciudad> findAllCiudad();

    List<CategoriaArbitroResponseDto> findAllCategoriasArbitros();

    List<NacionalidadResponseDto> findAllNacionalidades();

    List<PosicionResponseDto> findAllPosiciones();

    List<StatusResponseDto> findAllStatuses();

    List<TiposAcontecimientoResponseDto> findAllTiposAcontecimientos();

    CategoriaArbitroResponseDto findCategoriaArbitroById(Short id);

    NacionalidadResponseDto findNacionalidadById(Short id);

    PosicionResponseDto findPosicionById(Short id);

    StatusResponseDto findStatusById(Short id);

    TiposAcontecimientoResponseDto findTipoAcontecimientoById(Short id);

    Estado findEstadoById(Short id);

    Ciudad findCiudadById(Short id);

    CategoriaArbitro findCategoriaArbitroEntityById(Short id);

    Nacionalidad findNacionalidadEntityById(Short id);

    Posicion findPosicionEntityById(Short id);

    Status findStatusEntityById(Short id);

    TiposAcontecimiento findTipoAcontecimientoEntityById(Short id);

    Torneo findTorneoEntityById(Long id);

    TipoTraspaso findTipoTraspasoEntityById(Short id);

}
