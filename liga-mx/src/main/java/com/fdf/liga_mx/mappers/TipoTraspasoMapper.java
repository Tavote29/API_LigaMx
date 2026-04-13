package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.response.TipoTraspasoResponseDTO;
import com.fdf.liga_mx.models.entitys.TipoTraspaso;

import org.springframework.stereotype.Component;

@Component
public class TipoTraspasoMapper {

    public TipoTraspasoResponseDTO toDto(TipoTraspaso entity){
        if (entity == null){
            return null;
        }
        return TipoTraspasoResponseDTO.builder()
                .id(entity.getId())
                .descripcion(entity.getDescripcion())
                .build();
    }
}
