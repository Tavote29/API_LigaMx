package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.LesionMapper;
import com.fdf.liga_mx.models.dtos.request.LesionRequestDto;
import com.fdf.liga_mx.models.dtos.response.LesionResponseDto;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Lesion;
import com.fdf.liga_mx.repository.ILesionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class LesionServiceImpl implements ILesionService{
    private ILesionRepository lesionRepository;
    private IJugadorService jugadorService;
    private LesionMapper lesionMapper;

    @Override
    @Transactional
    public LesionResponseDto save(LesionRequestDto request) {
        Jugador jugador = jugadorService.findById(request.getNuiJugador());

        Lesion lesion = lesionMapper.toEntity(request);
        lesion.setJugador(jugador);
        return lesionMapper.toDto(lesionRepository.saveAndFlush(lesion));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesion> findAll() {
        return lesionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LesionResponseDto> findAllDto() {
        return lesionRepository.findAll().stream().map(lesion -> lesionMapper.toDto(lesion)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Lesion findById(UUID uuid) {
        return lesionRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("error.lesion.not_found"));
    }

    @Override
    @Transactional(readOnly = true)
    public LesionResponseDto findDtoById(UUID uuid) {
        Lesion lesion = lesionRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("error.lesion.not_found"));
        return lesionMapper.toDto(lesion);
    }

    @Override
    @Transactional
    public LesionResponseDto update(LesionRequestDto request, UUID uuid) {
        Lesion lesion = lesionRepository.findById(uuid).orElseThrow(()->new NoSuchElementException("error.lesion.not_found"));
        if(!lesion.getDescripcionLesion().equals(request.getDescripcionLesion())){
            lesion.setDescripcionLesion(request.getDescripcionLesion());
        }
        if (!lesion.getFechaLesion().equals(request.getFechaLesion())){
            lesion.setFechaLesion(request.getFechaLesion());
        }
        if (!lesion.getFechaRecuperacion().equals(request.getFechaEstimadaRecuperacion())){
            lesion.setFechaRecuperacion(request.getFechaEstimadaRecuperacion());
        }
        return lesionMapper.toDto(lesionRepository.saveAndFlush(lesion));
    }
}
