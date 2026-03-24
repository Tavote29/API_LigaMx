package com.fdf.liga_mx.models.request;

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
public class ClubRequest implements Serializable {
    private String nombre;
    private short titulos;
    private LocalDate fechaFundacion;
    private short idCiudad;
    private String nombrePropietario;
    private short idDT;
    private short idEstadio;
}
