package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.response.*;

import java.util.List;

public interface ICatalogosService {

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


}
