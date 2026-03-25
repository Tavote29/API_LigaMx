package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.DTMapper;
import com.fdf.liga_mx.models.entitys.DT;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.repositories.ClubRepository;
import com.fdf.liga_mx.models.repositories.DTRepository;
import com.fdf.liga_mx.models.request.DTRequest;
import com.fdf.liga_mx.models.response.DTResponse;
import com.fdf.liga_mx.models.repositories.INacionalidadRepository;
import com.fdf.liga_mx.models.repositories.IStatusRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class DTService  implements IDTService{

    private final ClubRepository clubRepository;
    private final INacionalidadRepository nacionalidadRepository;
    private final IStatusRepository statusRepository;
    private final DTRepository dtRepository;
    private final DTMapper dtMapper;

    @Override
    public DTResponse create(DTRequest dtRequest) {
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
                .idPersona(persona)
                .club(club)
                .build();

        var dtPersisted = this.dtRepository.save(dt);
        log.info("Director Tecnico creado con id:{}",dtPersisted.getId());

        return dtMapper.entityToResponse(dtPersisted);
    }

    @Override
    public DTResponse read(Long aLong) {
        return null;
    }

    @Override
    public DTResponse update(DTRequest dtRequest, Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
