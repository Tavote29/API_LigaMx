package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.PersonaRequest;
import com.fdf.liga_mx.models.dtos.response.PersonaResponseDto;
import com.fdf.liga_mx.models.entitys.Persona;
import java.util.UUID;

public interface IPersonaService extends CrudService<PersonaRequest, PersonaResponseDto, Persona, UUID> {

    void delete(UUID id);
}
