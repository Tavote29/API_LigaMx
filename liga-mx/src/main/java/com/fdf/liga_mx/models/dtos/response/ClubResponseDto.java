package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class ClubResponseDto {
    private Short id;
    private Short nombreClub;
    private LocalDate fechaFundacion;
    private String propietario;
    private EstadoResponseDto idEstado;
    private CiudadResponseDto idCiudad;
    private DTResponseDto idDt;
    private EstadioResponseDto idEstadio;
    private List<JugadorResponseDto> jugadores;

}