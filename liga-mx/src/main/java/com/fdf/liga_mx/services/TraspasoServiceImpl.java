package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.TraspasoMapper;
import com.fdf.liga_mx.models.dtos.request.TraspasoRequestDTO;
import com.fdf.liga_mx.models.dtos.response.TraspasoResponseDto;
import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.TipoTraspaso;
import com.fdf.liga_mx.models.entitys.Traspaso;
import com.fdf.liga_mx.repository.ClubRepository;
import com.fdf.liga_mx.repository.JugadorRepository;
import com.fdf.liga_mx.repository.TraspasoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TraspasoServiceImpl implements ITraspasoService{
    private final TraspasoMapper traspasoMapper;
    private final TraspasoRepository traspasoRepository;
    private final ICatalogosService catalogosService;
    private final ClubRepository clubRepository;
    private final JugadorRepository jugadorRepository;

    @Override
    public TraspasoResponseDto save(TraspasoRequestDTO traspasoRequestDTO) {
        Jugador jugador = jugadorRepository.findById(traspasoRequestDTO.getNuiJugador()).orElseThrow();
        Club clubOrigen = clubRepository.findById(traspasoRequestDTO.getClubOrigen()).orElseThrow();
        Club clubDestino = clubRepository.findById(traspasoRequestDTO.getClubDestino()).orElseThrow();
        TipoTraspaso tipoTraspaso = catalogosService.findTipoTraspasoEntityById(traspasoRequestDTO.getTipo());

        Traspaso traspaso = traspasoMapper.toEntity(traspasoRequestDTO);
        traspaso.setJugador(jugador);
        traspaso.setClubOrigen(clubOrigen);
        traspaso.setClubDestino(clubDestino);
        traspaso.setTipo(tipoTraspaso);

        return traspasoMapper.toDto(traspasoRepository.saveAndFlush(traspaso));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Traspaso> findAll() {return traspasoRepository.findAll();}

    @Override
    @Transactional(readOnly = true)
    public List<TraspasoResponseDto> findAllDto() {
        return traspasoRepository.findAll().stream().map(traspaso -> traspasoMapper.toDto(traspaso)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Traspaso findById(UUID uuid) {
        return traspasoRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Elemento no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public TraspasoResponseDto findDtoById(UUID uuid) {
        Traspaso traspaso = traspasoRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Elemento no encontrado"));
        return traspasoMapper.toDto(traspaso);
    }

    @Override
    @Transactional
    public TraspasoResponseDto update(TraspasoRequestDTO traspasoRequestDTO, UUID uuid) {
        Traspaso traspaso = traspasoRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Elemento no encontrado"));
        if (!traspaso.getFechaTraspaso().equals(traspasoRequestDTO.getFechaTraspaso())){
            traspaso.setFechaTraspaso(traspasoRequestDTO.getFechaTraspaso());
        }
        if (!traspaso.getValor().equals(traspasoRequestDTO.getValor())){
            traspaso.setValor(traspasoRequestDTO.getValor());
        }
        if (!traspaso.getFechaFin().equals(traspasoRequestDTO.getFechaFin())){
            traspaso.setFechaFin(traspaso.getFechaFin());
        }
        if (!traspaso.getJugador().getId().equals(traspasoRequestDTO.getNuiJugador())){
            Jugador jugador = jugadorRepository.findById(traspasoRequestDTO.getNuiJugador()).orElseThrow();
            traspaso.setJugador(jugador);
        }
        if (!traspaso.getClubDestino().getId().equals(traspasoRequestDTO.getClubDestino())){
            Club clubDestino = clubRepository.findById(traspasoRequestDTO.getClubDestino()).orElseThrow();
            traspaso.setClubDestino(clubDestino);
        }
        if(!traspaso.getTipo().getId().equals(traspasoRequestDTO.getTipo())){
            TipoTraspaso tipoTraspaso = catalogosService.findTipoTraspasoEntityById(traspasoRequestDTO.getTipo());
            traspaso.setTipo(tipoTraspaso);
        }
        return traspasoMapper.toDto(traspasoRepository.saveAndFlush(traspaso));
    }

    @Override
    public void delete(UUID uuid) {

    }
}
