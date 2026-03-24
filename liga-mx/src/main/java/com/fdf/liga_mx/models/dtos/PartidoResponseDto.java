package com.fdf.liga_mx.models.dtos;

import lombok.Builder;
import lombok.Value;
import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class PartidoResponseDto {
    UUID id;
    ClubResponseDto idLocal;
    ClubResponseDto idVisitante;
    EstadioResponseDto idEstadio;
    ArbitroResponseDto idArbitroCentral;
    ArbitroResponseDto idCuartoArbitro;
    ArbitroResponseDto idArbitroAsistente1;
    ArbitroResponseDto idArbitroAsistente2;
    Instant fecha;
    StatusResponseDto idStatus;
}