package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.JugadorMapper;
import com.fdf.liga_mx.mappers.PersonaMapper;
import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.models.repositories.IPosicionRepository;
import com.fdf.liga_mx.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class JugadorServiceImpl implements IJugadorService{

    private final ClubRepository clubRepository;
    private final IPosicionRepository posicionRepository;
    private final JugadorRepository jugadorRepository;
    private final JugadorMapper jugadorMapper;
    private final PersonaMapper personaMapper;
    private final ICatalogosService catalogosService;

    @Override
    @Transactional
    public JugadorResponseDto save(JugadorRequest jugadorRequest) {

        Club club = clubRepository.findById(jugadorRequest.getId_club()).orElseThrow();
        Posicion posicion = posicionRepository.findById(jugadorRequest.getId_posicion()).orElseThrow();

        Persona persona = personaMapper.toEntity(jugadorRequest.getPersona());
        Jugador jugador = jugadorMapper.toEntity(jugadorRequest);
        jugador.setIdPersona(persona);
        jugador.setIdClub(club);
        jugador.setIdPosicion(posicion);

        return jugadorMapper.toDto(jugadorRepository.saveAndFlush(jugador));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JugadorResponseDto> findAllDto() {
        return jugadorRepository.findAll().stream().map(e -> jugadorMapper.toDto(e)).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public Jugador findById(Long id) {
        return jugadorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el jugador"));
    }

    @Override
    public JugadorResponseDto findDtoById(Long id) {
        Jugador jugador = jugadorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el jugador"));
        return jugadorMapper.toDto(jugador);
    }

    @Override
    @Transactional
    public JugadorResponseDto update(JugadorRequest jugadorRequest, Long id) {
        Jugador jugador = jugadorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el jugador"));
        Nacionalidad nacionalidad = catalogosService.findNacionalidadEntityById(jugadorRequest.getPersona().getIdNacionalidad());
        Status status = catalogosService.findStatusEntityById(jugadorRequest.getPersona().getIdStatus());

        if (!jugador.getDorsal().equals(jugadorRequest.getDorsal())) jugador.setDorsal(jugadorRequest.getDorsal());
        if (!jugador.getIdPosicion().getId().equals(jugadorRequest.getId_posicion())) {
            Posicion posicion = catalogosService.findPosicionEntityById(jugadorRequest.getId_posicion());
            jugador.setIdPosicion(posicion);
        }
        if (!jugador.getIdClub().getId().equals(jugadorRequest.getId_club())){
            Club club = clubRepository.findById(jugadorRequest.getId_club()).orElseThrow();
            jugador.setIdClub(club);
        }


        personaMapper.updateEntity(jugador.getIdPersona(), jugadorRequest.getPersona(),nacionalidad,status);



        return jugadorMapper.toDto(jugadorRepository.saveAndFlush(jugador));
    }

    @Override
    public void delete(Long id) {

    }
}
