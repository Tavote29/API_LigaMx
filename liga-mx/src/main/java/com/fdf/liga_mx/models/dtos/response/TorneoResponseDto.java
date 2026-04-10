package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class TorneoResponseDto {
    private Long id;
    private String nombreTorneo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String status;
}
