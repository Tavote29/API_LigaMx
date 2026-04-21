package com.fdf.liga_mx.models.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoTraspasoResponseDTO {
    private Short id;
    private String descripcion;
}
