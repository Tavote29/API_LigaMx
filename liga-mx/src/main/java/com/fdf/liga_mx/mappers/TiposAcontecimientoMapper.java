package com.fdf.liga_mx.mappers;


import com.fdf.liga_mx.models.dtos.response.TiposAcontecimientoResponseDto;
import com.fdf.liga_mx.models.entitys.TiposAcontecimiento;
import org.springframework.stereotype.Component;

@Component
public class TiposAcontecimientoMapper {



    public TiposAcontecimientoResponseDto toDto(TiposAcontecimiento entity) {
        if (entity == null) {
            return null;
        }
        return TiposAcontecimientoResponseDto.builder()
                .id(entity.getId())
                .descripcionTipo(entity.getDescripcionTipo())
                .build();
    }
}