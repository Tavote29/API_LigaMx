package com.fdf.liga_mx.models.dtos.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface getTablaCociente {
    String getClub();
    Integer getTPJ();
    Integer getPuntos();

    @JsonIgnore
    BigDecimal getCociente();

    @JsonProperty("cociente")
    default BigDecimal getCocienteFormateado() {
        return getCociente() != null
                ? getCociente().setScale(4, RoundingMode.HALF_UP)
                : null;
    }
}
