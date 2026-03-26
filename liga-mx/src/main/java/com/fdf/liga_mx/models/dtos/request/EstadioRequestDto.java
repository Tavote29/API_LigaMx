package com.fdf.liga_mx.models.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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


    private Short id;

    @NotBlank
    private String nombreEstadio;

    @NotBlank
    private String direccion;

    @Positive
    private Integer capacidad;

    @NotNull
    private Short idEstado;

    @NotNull
    private Short idCiudad;
}