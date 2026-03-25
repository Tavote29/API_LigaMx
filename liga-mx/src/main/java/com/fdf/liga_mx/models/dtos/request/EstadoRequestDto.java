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
public class EstadoRequestDto {
    @NotNull(message = "El id no puede ser nulo")
    private Short id;

    @NotBlank(message = "El nombre del estado no puede estar vacío")
    @Size(max = 150, message = "El nombre del estado no puede exceder los 150 caracteres")
    private String nombreEstado;
}