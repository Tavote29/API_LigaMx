package com.fdf.liga_mx.models.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CiudadResponseDto {
    Short id;
    String nombreCiudad;
    EstadoResponseDto idEstado;
}