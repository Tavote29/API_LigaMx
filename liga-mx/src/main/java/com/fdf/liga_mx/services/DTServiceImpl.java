package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.DTMapper;
import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.models.entitys.DT;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.repositories.INacionalidadRepository;
import com.fdf.liga_mx.models.repositories.IStatusRepository;
import com.fdf.liga_mx.repository.ClubRepository;
import com.fdf.liga_mx.repository.DTRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class DTServiceImpl implements IDTService{

    private final ClubRepository clubRepository;
    private final INacionalidadRepository nacionalidadRepository;
    private final IStatusRepository statusRepository;
    private final DTRepository dtRepository;
    private final DTMapper dtMapper;

    @Override
    public DTResponseDto save(DTRequest dtRequest) {
        var nacionalidad = nacionalidadRepository.findById(dtRequest.getPersona().getIdNacionalidad()).orElseThrow();
        var status = statusRepository.findById(dtRequest.getPersona().getIdStatus()).orElseThrow();

        var club = clubRepository.findById(dtRequest.getIdClub()).orElseThrow();
        var persona = Persona.builder()
                .id(UUID.randomUUID())
                .nombre(dtRequest.getPersona().getNombre())
                .fechaNacimiento(dtRequest.getPersona().getFechaNacimiento())
                .estatura(dtRequest.getPersona().getEstatura())
                .peso(dtRequest.getPersona().getPeso())
                .idStatus(status)
                .idNacionalidad(nacionalidad)
                .build();

        var dt = DT.builder()
                .persona(persona)
                .club(club)
                .build();

        var dtPersisted = this.dtRepository.save(dt);
        log.info("Director Tecnico creado con id:{}",dtPersisted.getId());

        return dtMapper.toDto(dtPersisted);
    }

    @Override
    public List<DT> findAll() {
        return List.of();
    }

    @Override
    public List<DTResponseDto> findAllDto() {
        return List.of();
    }

    @Override
    public DT findById(Long aLong) {
        return null;
    }

    @Override
    public DTResponseDto findDtoById(Long aLong) {
        return null;
    }

    @Override
    public DTResponseDto update(DTRequest dtRequest) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
