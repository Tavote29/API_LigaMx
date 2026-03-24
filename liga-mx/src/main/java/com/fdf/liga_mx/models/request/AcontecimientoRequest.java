package com.fdf.liga_mx.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AcontecimientoRequest implements Serializable {
    private short idTipo;
    private String minuto;
    private short idJugadorPrimario;
    private short idJugadorSecundario;
    private short idPartido;
}
