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
public class PosicionRequestDto {
    
    @NotNull(message = "El id no puede ser nulo")
    private Short id;

    @NotBlank(message = "La descripción de la posición no puede estar vacía")
    @Size(max = 50, message = "La descripción de la posición no puede exceder los 50 caracteres")
    private String descripcionPosicion;
}
