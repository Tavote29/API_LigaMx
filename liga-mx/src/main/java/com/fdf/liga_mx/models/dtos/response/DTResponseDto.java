package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DTResponseDto {
    private Long id;
    private Short tarjetasAmarillas;
    private Short tarjetasRojas;
    private PersonaResponseDto idPersona;
    private Short idClub;
    private String nombreClub;
}