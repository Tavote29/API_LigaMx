package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.TorneoRequestDto;
import com.fdf.liga_mx.models.dtos.response.TorneoResponseDto;
import com.fdf.liga_mx.models.entitys.Torneo;

public interface ITorneoService {

    TorneoResponseDto save(TorneoRequestDto request);

    TorneoResponseDto findActualTorneo();

    TorneoResponseDto updateStatus(Long torneoId, Short id);

    TorneoResponseDto findDtoById(Long id);

}
