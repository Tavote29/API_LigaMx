package com.fdf.liga_mx.models.dtos;

import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class PersonaResponseDto {
    UUID id;
    String nombre;
    LocalDate fechaNacimiento;
    String lugarNacimiento;
    BigDecimal estatura;
    BigDecimal peso;
    StatusResponseDto idStatus;
    NacionalidadResponseDto idNacionalidad;
}