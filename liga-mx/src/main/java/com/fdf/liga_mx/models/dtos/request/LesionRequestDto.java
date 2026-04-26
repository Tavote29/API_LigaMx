package com.fdf.liga_mx.models.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LesionRequestDto {
    @NotNull(message = "La descripcion de la lesion no debe ser nula")
    private String descripcionLesion;

    @NotNull(message = "La fecha de lesion no debe ser nula")
    private LocalDateTime fechaLesion;

    @NotNull(message = "El id del jugador no debe ser nulo")
    private Long nuiJugador;

    private LocalDateTime fechaEstimadaRecuperacion;
}
