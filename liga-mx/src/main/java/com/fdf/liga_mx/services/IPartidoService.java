package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.events.PartidoFinalizadoEvent;
import com.fdf.liga_mx.models.dtos.projection.getMarcadorPartido;
import com.fdf.liga_mx.models.dtos.request.PartidoRequest;
import com.fdf.liga_mx.models.dtos.response.PartidoResponseDto;
import com.fdf.liga_mx.models.dtos.response.ResumenCambiosDto;
import com.fdf.liga_mx.models.entitys.Partido;

import java.util.List;
import java.util.UUID;

public interface IPartidoService extends CrudService<PartidoRequest, PartidoResponseDto, Partido, UUID> {
    //search
    void finalizarPartido(PartidoFinalizadoEvent event);
    List<getMarcadorPartido> obtenerMarcadorPartido(UUID uuid);

    List<ResumenCambiosDto> obtenerResumenCambios(UUID uuid);
}
