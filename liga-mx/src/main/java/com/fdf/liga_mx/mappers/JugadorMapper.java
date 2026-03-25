package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.response.JugadorResponse;
import com.fdf.liga_mx.models.response.PersonaResponse;
import org.springframework.stereotype.Component;

@Component
public class JugadorMapper {
    public JugadorResponse entityToResponse(Jugador entity){
        if (entity == null){
            return null;
        }
        JugadorResponse response = new JugadorResponse();
        response.setId(entity.getId());
        response.setDorsal(entity.getDorsal());
        response.setId_club(entity.getIdClub().getId());
        response.setId_posicion(entity.getIdPosicion().getId());

        PersonaMapper personaMapper = new PersonaMapper();
        PersonaResponse persona = personaMapper.entityToResponse(entity.getIdPersona());
        response.setPersona(persona);
        /*
        PersonaResponse persona = new PersonaResponse();
        persona.setNombre(entity.getIdPersona().getNombre());
        persona.setEstatura(entity.getIdPersona().getEstatura());
        persona.setPeso(entity.getIdPersona().getPeso());
        persona.setFechaNacimiento(entity.getIdPersona().getFechaNacimiento());
        persona.setIdNacionalidad(entity.getIdPersona().getIdNacionalidad().getId());

        response.setPersona(persona);
        */

        return response;
    }

}
