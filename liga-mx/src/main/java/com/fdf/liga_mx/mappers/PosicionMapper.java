package com.fdf.liga_mx.mappers;


import com.fdf.liga_mx.models.dtos.PosicionResponseDto;

import com.fdf.liga_mx.models.entitys.Posicion;
import org.springframework.stereotype.Component;

@Component
public class PosicionMapper {


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