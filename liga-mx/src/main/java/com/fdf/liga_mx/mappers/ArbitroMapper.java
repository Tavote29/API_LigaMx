package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.entitys.Arbitro;
import com.fdf.liga_mx.models.response.ArbitroResponse;
import com.fdf.liga_mx.models.response.PersonaResponse;
import org.springframework.stereotype.Component;

@Component
public class ArbitroMapper {
    public ArbitroResponse entityToResponse (Arbitro entity){
        if (entity == null){
            return null;
        }
        ArbitroResponse response = new ArbitroResponse();
        response.setIdArbitro(entity.getId());
        response.setFechaIncorporacion(entity.getFechaIncorporacion());

        PersonaMapper personaMapper = new PersonaMapper();
        PersonaResponse persona = personaMapper.entityToResponse(entity.getIdPersona());
        response.setPersonaResponse(persona);


        return response;
    }
}
