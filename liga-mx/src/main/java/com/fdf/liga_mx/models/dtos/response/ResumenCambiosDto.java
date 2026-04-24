package com.fdf.liga_mx.models.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

public record ResumenCambiosDto(
        @JsonProperty("id_club") Short idClub,
        @JsonProperty("total_cambios") Integer totalCambios,
        @JsonProperty("detalles_cambios") List<DetalleCambioDto> detalles
) {}
