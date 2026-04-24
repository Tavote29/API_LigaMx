package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class TraspasoResponseDto {
    private UUID uuid;
    private JugadorResponseDto nuiJugador;
    private ClubResponseDto clubOrigen;
    private ClubResponseDto clubDestino;
    private BigDecimal valor;
    private LocalDate fechaTraspaso;
    private LocalDate fechaFin;
    private TipoTraspasoResponseDTO tipo;
}
