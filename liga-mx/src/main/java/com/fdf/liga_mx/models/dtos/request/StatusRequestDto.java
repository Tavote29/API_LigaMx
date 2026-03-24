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
public class StatusRequestDto {

    @NotNull(message = "El id no puede ser nulo")
    private Short id;

    @NotBlank(message = "La descripción del status no puede estar vacía")
    @Size(max = 100, message = "La descripción del status no puede exceder los 100 caracteres")
    private String descripcionStatus;
}
