package com.fdf.liga_mx.models.dtos.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class LesionResponseDto {
    private UUID idLesion;
    private String descripcionLesion;
    private LocalDateTime fechaLesion;
    private LocalDateTime fechaRecuperacion;
    private JugadorResponseDto jugador;
}
