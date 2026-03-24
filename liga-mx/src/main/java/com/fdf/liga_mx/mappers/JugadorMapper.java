package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.request.JugadorRequest;
import com.fdf.liga_mx.models.request.PersonaRequest;
import org.springframework.stereotype.Component;

@Component
public class JugadorMapper {

    PersonaRequest persona = new PersonaRequest();

    public JugadorRequest toDTo(Jugador entity){
        if (entity == null){
            return JugadorRequest.builder()
                    .personaRequest(persona)
                    .dorsal(entity.getDorsal())
                    .id_club(entity.getIdClub().getId())
                    .build();
        }
        return null;
    }
}
