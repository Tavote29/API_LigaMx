package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.EstadoRequestDto;
import com.fdf.liga_mx.models.dtos.response.EstadoResponseDto;
import com.fdf.liga_mx.models.entitys.Estado;
import org.springframework.stereotype.Component;

@Component
public class EstadoMapper {

    public Estado toEntity(EstadoRequestDto request) {
        if (request == null) {
            return null;
        }
        return Estado.builder()
                .id(request.getId())
                .nombreEstado(request.getNombreEstado())
                .build();
    }

    public EstadoResponseDto toDto(Estado entity) {
        if (entity == null) {
            return null;
        }
        return EstadoResponseDto.builder()
                .id(entity.getId())
                .nombreEstado(entity.getNombreEstado())
                .build();
    }
}