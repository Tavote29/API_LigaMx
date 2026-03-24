package com.fdf.liga_mx.models.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DTResponse implements Serializable {
    private long idDT;
    private PersonaResponse persona;
}
