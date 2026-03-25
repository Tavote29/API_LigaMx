package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;

@Value
@Builder
public class ArbitroResponseDto {
    private Long id;
    private PersonaResponseDto idPersona;
    private LocalDate fechaIncorporacion;
    private CategoriaArbitroResponseDto idCategoria;
}