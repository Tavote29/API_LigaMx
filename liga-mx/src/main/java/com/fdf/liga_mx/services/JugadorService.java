package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.JugadorMapper;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.repositories.*;
import com.fdf.liga_mx.models.request.JugadorRequest;
import com.fdf.liga_mx.models.response.JugadorResponse;
import com.fdf.liga_mx.models.repositories.INacionalidadRepository;
import com.fdf.liga_mx.models.repositories.IPosicionRepository;
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
public class JugadorService implements IJugadorService{

    private final INacionalidadRepository nacionalidadRepository;
    private final ClubRepository clubRepository;
    private final IStatusRepository statusRepository;
    private final IPosicionRepository posicionRepository;
    private final JugadorRepository jugadorRepository;
    private final JugadorMapper jugadorMapper;

    @Override
    public JugadorResponse create(JugadorRequest jugadorRequest) {
        var nacionalidad = nacionalidadRepository.findById(jugadorRequest.getPersonaRequest().getIdNacionalidad()).orElseThrow();
        var status = statusRepository.findById(jugadorRequest.getPersonaRequest().getIdStatus()).orElseThrow();

        var club = clubRepository.findById(jugadorRequest.getIdClub()).orElseThrow();
        var posicion = posicionRepository.findById(jugadorRequest.getIdPosicion()).orElseThrow();

        var persona = Persona.builder()
                .id(UUID.randomUUID())
                .nombre(jugadorRequest.getPersonaRequest().getNombre())
                .fechaNacimiento(jugadorRequest.getPersonaRequest().getFechaNacimiento())
                .estatura(jugadorRequest.getPersonaRequest().getEstatura())
                .peso(jugadorRequest.getPersonaRequest().getPeso())
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
        return jugadorMapper.entityToResponse(jugadorPersisted);
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
