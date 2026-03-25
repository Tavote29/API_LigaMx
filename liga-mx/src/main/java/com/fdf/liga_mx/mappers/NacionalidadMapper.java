package com.fdf.liga_mx.mappers;


import com.fdf.liga_mx.models.dtos.response.NacionalidadResponseDto;
import com.fdf.liga_mx.models.entitys.Nacionalidad;
import org.springframework.stereotype.Component;

@Component
public class NacionalidadMapper {



    public NacionalidadResponseDto toDto(Nacionalidad entity) {
        if (entity == null) {
            return null;
        }
        return NacionalidadResponseDto.builder()
                .id(entity.getId())
                .nombreNacionalidad(entity.getNombreNacionalidad())
                .build();
    }
}