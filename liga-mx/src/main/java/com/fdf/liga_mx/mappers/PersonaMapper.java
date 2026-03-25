package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.response.PersonaResponse;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {
    public PersonaResponse entityToResponse(Persona entity){
        if (entity == null){
            return null;
        }
        PersonaResponse persona = new PersonaResponse();
        persona.setNombre(entity.getNombre());
        persona.setEstatura(entity.getEstatura());
        persona.setPeso(entity.getPeso());
        persona.setFechaNacimiento(entity.getFechaNacimiento());
        persona.setIdNacionalidad(entity.getIdNacionalidad().getId());

        return persona;
    }
}
