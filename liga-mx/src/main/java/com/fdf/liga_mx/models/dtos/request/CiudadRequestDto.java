package com.fdf.liga_mx.models.dtos.request;

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
public class CiudadRequestDto {
    @NotNull(message = "El id no puede ser nulo")
    private Short id;

    @NotBlank(message = "El nombre de la ciudad no puede estar vacío")
    @Size(max = 200, message = "El nombre de la ciudad no puede exceder los 200 caracteres")
    private String nombreCiudad;

    @NotNull(message = "El estado no puede ser nulo")
    private Short idEstado;
}