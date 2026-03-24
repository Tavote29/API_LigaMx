package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CiudadResponseDto {
    private Short id;
    private String nombreCiudad;
    private EstadoResponseDto idEstado;
}