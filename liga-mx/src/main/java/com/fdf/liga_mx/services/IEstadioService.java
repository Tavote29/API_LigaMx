package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.EstadioResponseDto;
import com.fdf.liga_mx.models.entitys.Estadio;
import org.springframework.data.domain.Page;


public interface IEstadioService extends CrudService<EstadioRequestDto, EstadioResponseDto, Estadio, Short> {

    Page<EstadioResponseDto> searchStadium(Integer page,Integer size,String sorts,Short ciudadId,Short estadoId,String nombre, Integer minCapacity, Integer maxCapacity);

}
