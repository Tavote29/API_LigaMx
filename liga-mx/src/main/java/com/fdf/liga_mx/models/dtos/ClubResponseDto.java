package com.fdf.liga_mx.models.dtos;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;

@Value
@Builder
public class ClubResponseDto {
    Short id;
    Short nombreClub;
    LocalDate fechaFundacion;
    String propietario;
    EstadoResponseDto idEstado;
    CiudadResponseDto idCiudad;
    DTResponseDto idDt;
    EstadioResponseDto idEstadio;
}