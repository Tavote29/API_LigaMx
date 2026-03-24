package com.fdf.liga_mx.models.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class PersonaRequest implements Serializable {
    private String nombre;
    private LocalDate fechaNacimiento;
    private String lugarNacimiento;
    private BigDecimal estatura;
    private BigDecimal peso;
    private short idStatus;
    private short idNacionalidad;
}

