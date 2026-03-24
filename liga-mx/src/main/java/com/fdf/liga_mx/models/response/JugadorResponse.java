package com.fdf.liga_mx.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JugadorResponse implements Serializable {
    private long id;
    private PersonaResponse persona;
    private short dorsal;
    private short id_posicion;
    private short id_club;
}
