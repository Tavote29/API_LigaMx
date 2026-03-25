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
    private LocalDate fechaFundacion;
    private int idCiudad;
    private String Propietario;
    private Long idDT;
    private short idEstadio;
}
