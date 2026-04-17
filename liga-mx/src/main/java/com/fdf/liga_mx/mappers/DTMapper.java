package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.models.entitys.DT;
import com.fdf.liga_mx.models.entitys.Persona;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DTMapper {

    private final PersonaMapper personaMapper;

    public DT toEntity(DTRequest request) {
        if (request == null) {
            return null;
        }
        Persona persona = request.getPersona() != null ? personaMapper.toEntity(request.getPersona()) : null;

        return DT.builder()
                .persona(persona)
                .build();
    }

    public DTResponseDto toDto(DT entity) {
        if (entity == null) {
            return null;
        }
        return DTResponseDto.builder()
                .id(entity.getId())
                .tarjetasAmarillas(entity.getTarjetasAmarillas())
                .tarjetasRojas(entity.getTarjetasRojas())
                .idPersona(personaMapper.toDto(entity.getPersona()))
                .idClub(entity.getClub() != null ? entity.getClub().getId() : null)
                .nombreClub(entity.getClub() != null ? entity.getClub().getNombreClub() : null)
                .build();
    }
}
