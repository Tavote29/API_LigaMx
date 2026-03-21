package com.fdf.liga_mx.mappers;


import com.fdf.liga_mx.models.dtos.StatusResponseDto;
import com.fdf.liga_mx.models.entitys.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {



    public StatusResponseDto toDto(Status entity) {
        if (entity == null) {
            return null;
        }
        return StatusResponseDto.builder()
                .id(entity.getId())
                .descripcionStatus(entity.getDescripcionStatus())
                .build();
    }
}