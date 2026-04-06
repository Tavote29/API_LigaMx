package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class PersonaResponseDto {
    private UUID id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String lugarNacimiento;
    private String imageUrl;
    private BigDecimal estatura;
    private BigDecimal peso;
    private StatusResponseDto idStatus;
    private NacionalidadResponseDto idNacionalidad;
}