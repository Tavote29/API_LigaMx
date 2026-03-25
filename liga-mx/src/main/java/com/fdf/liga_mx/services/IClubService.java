package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.ClubRequest;
import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.ClubResponseDto;
import com.fdf.liga_mx.models.entitys.Club;

import java.util.List;

public interface IClubService extends CrudService<ClubRequest, ClubResponseDto, Club, Short> {


    ClubResponseDto changeStadium(EstadioRequestDto estadioRequestDto);

    void assignDT(DTRequest dtRequest);

    List<ClubResponseDto> findByEstadoId(Short id);

    List<ClubResponseDto> findByCiudadId(Short id);

}
