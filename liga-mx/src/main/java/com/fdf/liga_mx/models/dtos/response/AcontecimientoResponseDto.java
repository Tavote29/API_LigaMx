package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;
import java.util.UUID;

@Value
@Builder
public class AcontecimientoResponseDto {
    UUID id;
    TiposAcontecimientoResponseDto idTipo;
    String minuto;
    JugadorResponseDto idJugadorPrimario;
    JugadorResponseDto idJugadorSecundario;
    PartidoResponseDto idPartido;
}