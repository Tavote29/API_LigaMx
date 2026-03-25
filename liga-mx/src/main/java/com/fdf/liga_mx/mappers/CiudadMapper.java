package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.CiudadRequestDto;
import com.fdf.liga_mx.models.dtos.response.CiudadResponseDto;
import com.fdf.liga_mx.models.entitys.Ciudad;
import com.fdf.liga_mx.models.entitys.Estado;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CiudadMapper {

    private final EstadoMapper estadoMapper;

    public Ciudad toEntity(CiudadRequestDto request) {
        if (request == null) {
            return null;
        }
        Estado estado = null;

        if (request.getIdEstado() != null) {
            estado = Estado.builder().id(request.getIdEstado()).build();
        }

        return Ciudad.builder()
                .id(request.getId())
                .nombreCiudad(request.getNombreCiudad())
                .idEstado(estado)
                .build();
    }

    public CiudadResponseDto toDto(Ciudad entity) {
        if (entity == null) {
            return null;
        }
        return CiudadResponseDto.builder()
                .id(entity.getId())
                .nombreCiudad(entity.getNombreCiudad())
                .idEstado(estadoMapper.toDto(entity.getIdEstado()))
                .build();
    }
}