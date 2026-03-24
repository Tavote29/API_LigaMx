package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;

@Value
@Builder
public class ArbitroResponseDto {
    Long id;
    PersonaResponseDto idPersona;
    LocalDate fechaIncorporacion;
    CategoriaArbitroResponseDto idCategoria;
}