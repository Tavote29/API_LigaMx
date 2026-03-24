package com.fdf.liga_mx.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTRequestDto {
    @NotNull(message = "El id no puede ser nulo")
    private Long id;

    private Short tarjetasAmarillas;

    private Short tarjetasRojas;

    @NotNull(message = "La persona no puede ser nula")
    private UUID idPersona;
}