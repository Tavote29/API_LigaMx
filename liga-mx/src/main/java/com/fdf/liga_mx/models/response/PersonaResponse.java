package com.fdf.liga_mx.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PersonaResponse implements Serializable {
    private UUID uuid;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String lugarNacimiento;
    private BigDecimal estatura;
    private BigDecimal peso;
    private short idStatus;
    private short idNacionalidad;
}
