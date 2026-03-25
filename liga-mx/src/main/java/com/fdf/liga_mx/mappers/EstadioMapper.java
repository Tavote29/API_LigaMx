package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.EstadioResponseDto;
import com.fdf.liga_mx.models.entitys.Ciudad;
import com.fdf.liga_mx.models.entitys.Estadio;
import com.fdf.liga_mx.models.entitys.Estado;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstadioMapper {

    private final EstadoMapper estadoMapper;
    private final CiudadMapper ciudadMapper;

    public Estadio toEntity(EstadioRequestDto request) {
        if (request == null) {
            return null;
        }
        Estado estado = request.getIdEstado() != null ? Estado.builder().id(request.getIdEstado()).build() : null;
        Ciudad ciudad = request.getIdCiudad() != null ? Ciudad.builder().id(request.getIdCiudad()).build() : null;

        return Estadio.builder()
                .nombreEstadio(request.getNombreEstadio())
                .direccion(request.getDireccion())
                .capacidad(request.getCapacidad())
                .idEstado(estado)
                .idCiudad(ciudad)
                .build();
    }

    public EstadioResponseDto toDto(Estadio entity) {
        if (entity == null) {
            return null;
        }
        return EstadioResponseDto.builder()
                .id(entity.getId())
                .nombreEstadio(entity.getNombreEstadio())
                .direccion(entity.getDireccion())
                .capacidad(entity.getCapacidad())
                .idEstado(estadoMapper.toDto(entity.getIdEstado()))
                .idCiudad(ciudadMapper.toDto(entity.getIdCiudad()))
                .build();
    }
}