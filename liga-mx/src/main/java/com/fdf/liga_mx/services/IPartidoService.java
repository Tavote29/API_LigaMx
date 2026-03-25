package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.PartidoRequest;
import com.fdf.liga_mx.models.dtos.response.PartidoResponseDto;
import com.fdf.liga_mx.models.entitys.Partido;
import java.util.UUID;

public interface IPartidoService extends CrudService<PartidoRequest, PartidoResponseDto, Partido, UUID> {
}
