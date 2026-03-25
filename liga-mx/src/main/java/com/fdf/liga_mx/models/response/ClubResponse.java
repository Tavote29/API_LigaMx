package com.fdf.liga_mx.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClubResponse implements Serializable {
    private short idClub;
    private String nombre;
    private int idCiudad;
    private short idEstado;
    private String propietario;
    private LocalDate fechaFundacion;
}
