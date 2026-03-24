package com.fdf.liga_mx.models.request;

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
public class ArbitroRequest implements Serializable {
    private PersonaRequest persona;
    private LocalDate fechaIncorporacion;
    private short idCategoria;
}
