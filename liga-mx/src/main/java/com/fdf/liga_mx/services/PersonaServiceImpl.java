package com.fdf.liga_mx.services;


import com.fdf.liga_mx.mappers.PersonaMapper;
import com.fdf.liga_mx.models.dtos.request.PersonaRequest;
import com.fdf.liga_mx.models.dtos.response.PersonaResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.repository.PersonaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class PersonaServiceImpl implements IPersonaService {

    private final ICatalogosService catalogosService;
    private final PersonaMapper personaMapper;
    private final PersonaRepository personaRepository;

    @Transactional
    @Override
    public PersonaResponseDto save(PersonaRequest request) {
        Nacionalidad nacionalidad = catalogosService.findNacionalidadEntityById(request.getIdNacionalidad());
        Status status = catalogosService.findStatusEntityById(request.getIdStatus());
        Persona persona = personaMapper.toEntity(request);

        persona.setIdNacionalidad(nacionalidad);
        persona.setIdStatus(status);
        log.info("Persona creado con el id: {}",persona.getId());

        return personaMapper.toDto(personaRepository.saveAndFlush(persona));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDto> findAllDto() {
        return personaRepository.findAll().stream().map(persona -> personaMapper.toDto(persona)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Persona findById(UUID id) {
        return personaRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro la persona indicada"));
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaResponseDto findDtoById(UUID id) {
        Persona persona = personaRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro la persona indicada"));
        return personaMapper.toDto(persona);
    }

    @Override
    @Transactional
    public PersonaResponseDto update(PersonaRequest request, UUID id) {
        Persona persona = personaRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro la persona indicada"));
        if (!persona.getNombre().equals(request.getNombre())) persona.setNombre(request.getNombre());
        if (!persona.getFechaNacimiento().equals(request.getFechaNacimiento())) persona.setFechaNacimiento(request.getFechaNacimiento());
        if (!persona.getLugarNacimiento().equals(request.getLugarNacimiento())) persona.setLugarNacimiento(request.getLugarNacimiento());
        if (!persona.getPeso().equals(request.getPeso())) persona.setPeso(request.getPeso());
        if (!persona.getIdNacionalidad().getId().equals(request.getIdNacionalidad())){
            Nacionalidad nacionalidad = catalogosService.findNacionalidadEntityById(request.getIdNacionalidad());
            persona.setIdNacionalidad(nacionalidad);
        }
        if (!persona.getIdNacionalidad().getId().equals(request.getIdNacionalidad())){
            Status status = catalogosService.findStatusEntityById(request.getIdStatus());
            persona.setIdStatus(status);
        }
        log.info("Persona modificado");
        return personaMapper.toDto(personaRepository.saveAndFlush(persona));
    }

    @Override
    public void delete(UUID id) {

    }
}
