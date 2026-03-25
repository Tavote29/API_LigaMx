package com.fdf.liga_mx.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ArbitroResponse implements Serializable {
    private long idArbitro;
    private LocalDate fechaIncorporacion;
    private PersonaResponse personaResponse;
}
