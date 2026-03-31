package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.DTMapper;
import com.fdf.liga_mx.mappers.PersonaMapper;
import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.repository.ClubRepository;
import com.fdf.liga_mx.repository.DTRepository;
import com.fdf.liga_mx.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class DTServiceImpl implements IDTService{

    private final ClubRepository clubRepository;
    private final ICatalogosService catalogosService;
    private final DTRepository dtRepository;
    private final DTMapper dtMapper;
    private final PersonaMapper personaMapper;

    @Override
    @Transactional
    public DTResponseDto save( DTRequest dtRequest) {
        Club club = clubRepository.findById(dtRequest.getIdClub()).orElseThrow();

        Nacionalidad nacionalidad = catalogosService.findNacionalidadEntityById(dtRequest.getPersona().getIdNacionalidad());
        Status status = catalogosService.findStatusEntityById(dtRequest.getPersona().getIdStatus());

        Persona persona = personaMapper.toEntity(dtRequest.getPersona());
        persona.setIdNacionalidad(nacionalidad);
        persona.setIdStatus(status);


        DT dt = dtMapper.toEntity(dtRequest);
        dt.setPersona(persona);
        dt.setClub(club);
        return dtMapper.toDto(dtRepository.saveAndFlush(dt));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DT> findAll() {
        return dtRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DTResponseDto> findAllDto() {
        return dtRepository.findAll().stream().map(dt -> dtMapper.toDto(dt)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DT findById(Long id) {
        return dtRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro al director tecnico"));
    }

    @Override
    @Transactional(readOnly = true)
    public DTResponseDto findDtoById(Long id) {
        DT dt = dtRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro al director tecnico"));
        return dtMapper.toDto(dt);
    }

    @Override
    @Transactional
    public DTResponseDto update(DTRequest dtRequest, Long id) {
        DT dt = dtRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro el DT indicado"));
        Nacionalidad nacionalidad = catalogosService.findNacionalidadEntityById(dtRequest.getPersona().getIdNacionalidad());
        Status status = catalogosService.findStatusEntityById(dtRequest.getPersona().getIdStatus());

        /*
        if (!dt.getClub().getId().equals(dtRequest.getIdClub())){
            Club club = clubRepository.findById(dtRequest.getIdClub()).orElseThrow(()-> new NoSuchElementException("No se encontro el DT indicado"));
            dt.setClub(club);
        }
        */
        personaMapper.updateEntity(dt.getPersona(),dtRequest.getPersona(),nacionalidad,status);

        return dtMapper.toDto(dtRepository.saveAndFlush(dt));
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<DTResponseDto> searchDT(Integer page, Integer size, String sorts, String nombre, Integer nacionalidad, Short club) {
        Pageable pageable = PageRequest.of(page,size, Utils.parseSortParams(sorts));
        Page<DT> dtPage = dtRepository.searchDT(pageable,nombre,nacionalidad,club);
        return dtPage.map(dt-> dtMapper.toDto(dt));
    }
}
