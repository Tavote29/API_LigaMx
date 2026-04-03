package com.fdf.liga_mx.models.dtos.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PersonaRequest implements Serializable {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 255)
    private String nombre;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El lugar de nacimiento no puede estar vacío")
    @Size(max = 255)
    private String lugarNacimiento;

    @NotNull(message = "La estatura no puede ser nula")
    private BigDecimal estatura;

    @NotNull(message = "El peso no puede ser nulo")
    private BigDecimal peso;


    private Short idStatus;

    @NotNull(message = "La nacionalidad no puede ser nula")
    private Short idNacionalidad;
}

