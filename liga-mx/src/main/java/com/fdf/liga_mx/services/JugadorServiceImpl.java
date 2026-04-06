package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.JugadorMapper;
import com.fdf.liga_mx.mappers.PersonaMapper;
import com.fdf.liga_mx.models.dtos.projection.TarjetasResumenPorTorneo;
import com.fdf.liga_mx.models.dtos.projection.getTarjetasPorPartido;
import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.models.repositories.IPosicionRepository;
import com.fdf.liga_mx.repository.*;
import com.fdf.liga_mx.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
    private final MediaStorageService mediaService;

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
    @Transactional(readOnly = true)
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
    @Transactional
    public void delete(Long id) {

    }

    @Override
    public Page<JugadorResponseDto> searchJugador(Integer page, Integer size, String sorts, String nombre, Integer nacionalidad, Short club) {
        Pageable pageable = PageRequest.of(page,size, Utils.parseSortParams(sorts));
        Page<Jugador> jugadorPage = jugadorRepository.searchJugador(pageable,nombre,nacionalidad, club);
        return jugadorPage.map(j-> jugadorMapper.toDto(j));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Integer> obtenerTarjetasJugadorPorTorneoId(Long jugadorId, Long torneoId) {

        TarjetasResumenPorTorneo resumen = jugadorRepository.obtenerTarjetasJugadorPorTorneoId(jugadorId, torneoId);

        Map<String, Integer> tarjetas = new HashMap<>();

        tarjetas.put("tarjetas_amarillas", resumen.getTarjetas_amarillas() != null ? resumen.getTarjetas_amarillas() : 0);
        tarjetas.put("tarjetas_rojas", resumen.getTarjetas_rojas() != null ? resumen.getTarjetas_rojas() : 0);
        tarjetas.put("faltas_cometidas", resumen.getFaltas_cometidas() != null ? resumen.getFaltas_cometidas() : 0);

        return tarjetas;
    }

    @Override
    @Transactional
    public void updateTarjetasByPartidoId(UUID id) {

        List<getTarjetasPorPartido> tarjetasPorPartido = jugadorRepository.obtenerTarjetasPorPartidoId(id.toString());

        for (getTarjetasPorPartido tarjetas : tarjetasPorPartido) {
            Jugador jugador = jugadorRepository.findById(tarjetas.getId_jugador()).orElseThrow(() ->  new NoSuchElementException("No se encontro el jugador"));

            jugador.setTarjetasAmarillas((short) (jugador.getTarjetasAmarillas()+tarjetas.getTarjetas_amarillas()));
            jugador.setTarjetasRojas((short) (jugador.getTarjetasRojas()+tarjetas.getTarjetas_rojas()));

            jugadorRepository.save(jugador);

        }

    }

    @Override
    @Transactional
    public JugadorResponseDto save(JugadorRequest jugadorRequest, MultipartFile file) throws IOException {

        Club club = clubRepository.findById(jugadorRequest.getId_club()).orElseThrow(() -> new NoSuchElementException("No se encontro el club"));
        Posicion posicion = posicionRepository.findById(jugadorRequest.getId_posicion()).orElseThrow(() -> new NoSuchElementException("No se encontro la posicion"));


        Persona persona = personaMapper.toEntity(jugadorRequest.getPersona());
        Jugador jugador = jugadorMapper.toEntity(jugadorRequest);
        jugador.setIdPersona(persona);
        jugador.setIdClub(club);
        jugador.setIdPosicion(posicion);

        Jugador jugadorSaved = jugadorRepository.saveAndFlush(jugador);


        String storageKey = mediaService.uploadFile(file,jugadorSaved.getIdPersona().getId().toString());

        jugadorSaved.getIdPersona().setImageUrl(storageKey);

        return jugadorMapper.toDtoSinClub(jugadorRepository.save(jugadorSaved));
    }
}
