package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.response.TorneoResponseDto;
import com.fdf.liga_mx.models.entitys.Torneo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TorneoMapper {
    public TorneoResponseDto toDto(Torneo entity){
        if (entity == null){
            return null;
        }
        return TorneoResponseDto.builder()
                .id(entity.getId())
                .nombreTorneo(entity.getNombreTorneo())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .status(entity.getStatus())
                .build();
    }

}
