package com.fdf.liga_mx.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PartidoRequest implements Serializable {
    private short idEquipoLocal;
    private short idEquipoVisitante;
    private short idEstadio;
    private short idArbitro;
    private short idCuartoArbitro;
    private short idArbitroAsistente1;
    private short idArbitroAsistente2;
    private LocalDateTime fecha;
    private short idStatus;
}
