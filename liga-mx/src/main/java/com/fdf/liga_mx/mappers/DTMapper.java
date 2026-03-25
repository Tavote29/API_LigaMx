package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.entitys.DT;
import com.fdf.liga_mx.models.response.DTResponse;
import com.fdf.liga_mx.models.response.PersonaResponse;
import org.springframework.stereotype.Component;

@Component
public class DTMapper {
    public DTResponse entityToResponse(DT entity){
        if (entity == null){
            return null;
        }
        DTResponse response = new DTResponse();
        response.setIdDT(entity.getId());

        PersonaMapper personaMapper = new PersonaMapper();
        PersonaResponse persona =  personaMapper.entityToResponse(entity.getIdPersona());
        response.setPersona(persona);
        return response;
    }
}
