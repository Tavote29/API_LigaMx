package com.fdf.liga_mx.models.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JugadorResponseDto {
    Long id;
    Short tarjetasAmarillas;
    Short tarjetasRojas;
    Short dorsal;
    PersonaResponseDto idPersona;
    PosicionResponseDto idPosicion;
    ClubResponseDto idClub;
}