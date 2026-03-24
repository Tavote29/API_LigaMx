package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EstadioResponseDto {
    Short id;
    String nombreEstadio;
    String direccion;
    Integer capacidad;
    EstadoResponseDto idEstado;
    CiudadResponseDto idCiudad;
}