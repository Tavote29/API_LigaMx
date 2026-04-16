package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JugadorResponseDto {
    private Long id;
    private Short tarjetasAmarillas;
    private Short tarjetasRojas;
    private Short dorsal;
    private String club;
    private String image;
    private PersonaResponseDto idPersona;
    private PosicionResponseDto idPosicion;
    private ClubResponseDto idClub;
}