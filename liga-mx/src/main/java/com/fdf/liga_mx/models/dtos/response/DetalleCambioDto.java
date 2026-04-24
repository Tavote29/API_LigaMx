package com.fdf.liga_mx.models.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

public record DetalleCambioDto(
        @JsonProperty("jugador_in") Long jugadorIn,
        @JsonProperty("jugador_out") Long jugadorOut
) {}
