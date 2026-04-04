package com.fdf.liga_mx.models.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PartidoRequest implements Serializable {


    @NotNull(message = "El club local no puede ser nulo")
    private Short idLocal;

    @NotNull(message = "El club visitante no puede ser nulo")
    private Short idVisitante;

    @NotNull(message = "El estadio no puede ser nulo")
    private Short idEstadio;

    @NotNull(message = "El árbitro central no puede ser nulo")
    private Long idArbitroCentral;

    private Long idCuartoArbitro;

    private Long idArbitroAsistente1;

    private Long idArbitroAsistente2;

    @NotNull(message = "La fecha no puede ser nula")
    private Instant fecha;

    @NotNull(message = "El status no puede ser nulo")
    private Short idStatus;

    private Long idTorneo;
}
