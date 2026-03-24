package com.fdf.liga_mx.mappers;


import com.fdf.liga_mx.models.dtos.request.StatusRequestDto;
import com.fdf.liga_mx.models.dtos.response.StatusResponseDto;
import com.fdf.liga_mx.models.entitys.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

    public Status toEntity(StatusRequestDto request){
        if (request == null) {
            return null;
        }
        return Status.builder()
                .id(request.getId())
                .descripcionStatus(request.getDescripcionStatus()).build();
    }

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