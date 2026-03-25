package com.fdf.liga_mx.models.request;

import com.fdf.liga_mx.models.response.PersonaResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JugadorRequest implements Serializable {
    private PersonaRequest personaRequest;
    private short dorsal;
    private short idPosicion;
    private short idClub;
}
