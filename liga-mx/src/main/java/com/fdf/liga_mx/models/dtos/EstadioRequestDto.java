package com.fdf.liga_mx.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadioRequestDto {
    @NotNull(message = "El id no puede ser nulo")
    private Short id;

    @NotBlank(message = "El nombre del estadio no puede estar vacío")
    @Size(max = 250)
    private String nombreEstadio;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 300)
    private String direccion;

    @NotNull(message = "La capacidad no puede ser nula")
    private Integer capacidad;

    @NotNull(message = "El estado no puede ser nulo")
    private Short idEstado;

    @NotNull(message = "La ciudad no puede ser nula")
    private Short idCiudad;
}