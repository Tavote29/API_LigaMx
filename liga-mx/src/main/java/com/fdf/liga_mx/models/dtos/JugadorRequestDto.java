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
public class JugadorRequestDto {
    @NotNull(message = "El id no puede ser nulo")
    private Long id;

    private Short tarjetasAmarillas;

    private Short tarjetasRojas;

    @NotNull(message = "El dorsal no puede ser nulo")
    private Short dorsal;

    @NotNull(message = "La persona no puede ser nula")
    private UUID idPersona;

    @NotNull(message = "La posición no puede ser nula")
    private Short idPosicion;

    @NotNull(message = "El club no puede ser nulo")
    private Short idClub;
}