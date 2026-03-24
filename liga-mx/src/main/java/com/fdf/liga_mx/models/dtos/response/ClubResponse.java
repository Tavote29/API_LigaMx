package com.fdf.liga_mx.models.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClubResponse implements Serializable {
    private short idClub;
    private String nombre;
    private short idCiudad;
}
