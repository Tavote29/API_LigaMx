package com.fdf.liga_mx.models.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraspasoRequestDTO implements Serializable {
    @NotNull(message = "El id del jugador no debe ser nulo")
    private Long nuiJugador;

    private Short clubOrigen;
    private Short clubDestino;
    private BigDecimal valor;
    private LocalDate fechaTraspaso;
    private LocalDate fechaFin;
    private Short tipo;

}
