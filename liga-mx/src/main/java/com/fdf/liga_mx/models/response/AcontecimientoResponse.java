package com.fdf.liga_mx.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AcontecimientoResponse implements Serializable {
    private UUID uuid;
    private String minuto;
    private short idTipo;
    private short idPartido;
}
