package com.fdf.liga_mx.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArbitroRequestDto {
    @NotNull(message = "El id no puede ser nulo")
    private Long id;

    @NotNull(message = "La persona no puede ser nula")
    private UUID idPersona;

    @NotNull(message = "La fecha de incorporación no puede ser nula")
    private LocalDate fechaIncorporacion;

    @NotNull(message = "La categoría no puede ser nula")
    private Short idCategoria;
}