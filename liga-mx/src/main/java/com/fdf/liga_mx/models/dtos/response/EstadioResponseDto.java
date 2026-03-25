package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EstadioResponseDto {
    private Short id;
    private String nombreEstadio;
    private String direccion;
    private Integer capacidad;
    private EstadoResponseDto idEstado;
    private CiudadResponseDto idCiudad;
}