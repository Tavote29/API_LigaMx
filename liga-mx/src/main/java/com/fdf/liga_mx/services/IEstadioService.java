package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.EstadioResponseDto;
import com.fdf.liga_mx.models.entitys.Estadio;

public interface IEstadioService extends CrudService<EstadioRequestDto, EstadioResponseDto, Estadio, Short> {
}
