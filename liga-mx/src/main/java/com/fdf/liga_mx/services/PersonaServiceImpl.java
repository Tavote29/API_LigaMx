package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.PersonaRequest;
import com.fdf.liga_mx.models.dtos.response.PersonaResponseDto;
import com.fdf.liga_mx.models.entitys.Persona;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonaServiceImpl implements IPersonaService {

    @Override
    public PersonaResponseDto save(PersonaRequest request) {
        return null;
    }

    @Override
    public List<Persona> findAll() {
        return null;
    }

    @Override
    public List<PersonaResponseDto> findAllDto() {
        return null;
    }

    @Override
    public Persona findById(UUID id) {
        return null;
    }

    @Override
    public PersonaResponseDto findDtoById(UUID id) {
        return null;
    }

    @Override
    public PersonaResponseDto update(PersonaRequest request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
