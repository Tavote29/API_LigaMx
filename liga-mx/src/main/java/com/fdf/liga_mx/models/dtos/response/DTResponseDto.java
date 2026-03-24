package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DTResponseDto {
    Long id;
    Short tarjetasAmarillas;
    Short tarjetasRojas;
    PersonaResponseDto idPersona;
}