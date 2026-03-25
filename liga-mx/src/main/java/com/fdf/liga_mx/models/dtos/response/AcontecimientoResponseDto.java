package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;
import java.util.UUID;

@Value
@Builder
public class AcontecimientoResponseDto {
    private UUID id;
    private TiposAcontecimientoResponseDto idTipo;
    private String minuto;
    private JugadorResponseDto idJugadorPrimario;
    private JugadorResponseDto idJugadorSecundario;
    private PartidoResponseDto idPartido;
}