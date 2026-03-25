package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.JugadorMapper;
import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.repositories.INacionalidadRepository;
import com.fdf.liga_mx.models.repositories.IPosicionRepository;
import com.fdf.liga_mx.models.repositories.IStatusRepository;
import com.fdf.liga_mx.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class JugadorService implements IJugadorService{

    private final INacionalidadRepository nacionalidadRepository;
    private final ClubRepository clubRepository;
    private final IStatusRepository statusRepository;
    private final IPosicionRepository posicionRepository;
    private final JugadorRepository jugadorRepository;
    private final JugadorMapper jugadorMapper;



    @Override
    public JugadorResponseDto save(JugadorRequest jugadorRequest) {
        var nacionalidad = nacionalidadRepository.findById(jugadorRequest.getPersona().getIdNacionalidad()).orElseThrow();
        var status = statusRepository.findById(jugadorRequest.getPersona().getIdStatus()).orElseThrow();

        var club = clubRepository.findById(jugadorRequest.getId_club()).orElseThrow();
        var posicion = posicionRepository.findById(jugadorRequest.getId_posicion()).orElseThrow();

        var persona = Persona.builder()
                .id(UUID.randomUUID())
                .nombre(jugadorRequest.getPersona().getNombre())
                .fechaNacimiento(jugadorRequest.getPersona().getFechaNacimiento())
                .estatura(jugadorRequest.getPersona().getEstatura())
                .peso(jugadorRequest.getPersona().getPeso())
                .idStatus(status)
                .idNacionalidad(nacionalidad)
                .build();

        var jugador = Jugador.builder()
                .dorsal(jugadorRequest.getDorsal())
                .idPosicion(posicion)
                .idClub(club)
                .idPersona(persona)
                .build();

        var jugadorPersisted = this.jugadorRepository.save(jugador);

        log.info("Jugador creado con id:{}",jugadorPersisted.getId());
        return jugadorMapper.toDto(jugadorPersisted);
    }

    @Override
    public List<Jugador> findAll() {
        return List.of();
    }

    @Override
    public List<JugadorResponseDto> findAllDto() {
        return List.of();
    }


    @Override
    public Jugador findById(Long aLong) {
        return null;
    }

    @Override
    public JugadorResponseDto findDtoById(Long aLong) {
        return null;
    }

    @Override
    public JugadorResponseDto update(JugadorRequest jugadorRequest) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
