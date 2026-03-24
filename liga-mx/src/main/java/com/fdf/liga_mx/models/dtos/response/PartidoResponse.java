package com.fdf.liga_mx.models.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PartidoResponse implements Serializable {
    private UUID uuid;
    private LocalDateTime fecha;
    private short idEquipoLocal;
    private short idEquipoVisitante;
}
