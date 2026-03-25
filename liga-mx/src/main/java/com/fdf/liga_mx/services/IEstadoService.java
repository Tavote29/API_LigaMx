package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.EstadoRequestDto;
import com.fdf.liga_mx.models.dtos.response.EstadoResponseDto;
import com.fdf.liga_mx.models.entitys.Estado;

public interface IEstadoService extends CrudService<EstadoRequestDto, EstadoResponseDto, Estado, Short> {
}
