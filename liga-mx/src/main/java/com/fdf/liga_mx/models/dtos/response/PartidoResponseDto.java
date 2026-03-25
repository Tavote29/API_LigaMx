package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;
import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class PartidoResponseDto {
    private UUID id;
    private ClubResponseDto idLocal;
    private ClubResponseDto idVisitante;
    private EstadioResponseDto idEstadio;
    private ArbitroResponseDto idArbitroCentral;
    private ArbitroResponseDto idCuartoArbitro;
    private ArbitroResponseDto idArbitroAsistente1;
    private ArbitroResponseDto idArbitroAsistente2;
    private Instant fecha;
    private StatusResponseDto idStatus;
}