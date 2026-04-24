package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.TorneoMapper;
import com.fdf.liga_mx.models.dtos.request.TorneoRequestDto;
import com.fdf.liga_mx.models.dtos.response.TorneoResponseDto;
import com.fdf.liga_mx.models.entitys.Torneo;
import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.repository.ITorneoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TorneoServiceImpl implements ITorneoService{

    private final ITorneoRepository torneoRepo;

    private final TorneoMapper torneoMapper;

    @Override
    @Transactional
    public TorneoResponseDto save(TorneoRequestDto request) {
        Torneo torneo = Torneo.builder()
                .nombreTorneo(request.getNombreTorneo())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .status(Estados.ACTIVO.getCodigo())
                .build();
        
        return torneoMapper.toDto(torneoRepo.save(torneo));
    }

    @Override
    @Transactional(readOnly = true)
    public TorneoResponseDto findActualTorneo() {
        return torneoMapper.toDto(torneoRepo.findActualTorneo().orElseThrow(() -> new NoSuchElementException("error.catalogo.torneo_not_found")));
    }

    @Override
    @Transactional
    public TorneoResponseDto updateStatus(Long torneoId, Short id) {

        Estados.fromCode(id);

        Torneo torneo = torneoRepo.findById(torneoId)
                .orElseThrow(() -> new NoSuchElementException("error.catalogo.torneo_not_found"));
        
        torneo.setStatus(id);
        
        return torneoMapper.toDto(torneoRepo.save(torneo));
    }

    @Override
    @Transactional(readOnly = true)
    public TorneoResponseDto findDtoById(Long id) {
        return torneoMapper.toDto(torneoRepo.findById(id).orElseThrow(() -> new NoSuchElementException("error.catalogo.torneo_not_found")));
    }
}
