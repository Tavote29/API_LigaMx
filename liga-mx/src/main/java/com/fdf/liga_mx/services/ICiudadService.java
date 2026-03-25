package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.CiudadRequestDto;
import com.fdf.liga_mx.models.dtos.response.CiudadResponseDto;
import com.fdf.liga_mx.models.entitys.Ciudad;

public interface ICiudadService extends CrudService<CiudadRequestDto, CiudadResponseDto, Ciudad, Short> {
}
