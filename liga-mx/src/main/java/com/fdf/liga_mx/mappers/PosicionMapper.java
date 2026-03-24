package com.fdf.liga_mx.mappers;


import com.fdf.liga_mx.models.dtos.request.PosicionRequestDto;
import com.fdf.liga_mx.models.dtos.response.PosicionResponseDto;

import com.fdf.liga_mx.models.entitys.Posicion;
import org.springframework.stereotype.Component;

@Component
public class PosicionMapper {

    public Posicion toEntity(PosicionRequestDto request){
        if (request == null) {
            return null;
        }
        return Posicion.builder()
                .id(request.getId())
                .descripcionPosicion(request.getDescripcionPosicion()).build();
    }


    public PosicionResponseDto toDto(Posicion entity) {
        if (entity == null) {
            return null;
        }
        return PosicionResponseDto.builder()
                .id(entity.getId())
                .descripcionPosicion(entity.getDescripcionPosicion())
                .build();
    }
}