package com.fdf.liga_mx.models.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DTRequest implements Serializable {


    private Long NUI_DT;
    private PersonaRequest persona;
    private Short idClub;
}
