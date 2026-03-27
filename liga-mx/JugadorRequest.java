package com.fdf.liga_mx.models.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JugadorRequest implements Serializable {

    @NotNull(message = "La persona no puede ser nula")
    private PersonaRequest persona;

    @NotNull(message = "El dorsal no puede ser nulo")
    private Short dorsal;

    @NotNull(message = "La posición no puede ser nula")
    private Short id_posicion;

    @NotNull(message = "El club no puede ser nulo")
    private Short id_club;

    @Null
    private Short tarjetasAmarillas;

    @Null
    private Short tarjetasRojas;
}
