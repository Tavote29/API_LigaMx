package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.PosicionRequestDto;
import com.fdf.liga_mx.models.dtos.response.PosicionResponseDto;
import com.fdf.liga_mx.models.entitys.Posicion;

public interface IPosicionService extends CrudService<PosicionRequestDto, PosicionResponseDto, Posicion, Short> {
}
