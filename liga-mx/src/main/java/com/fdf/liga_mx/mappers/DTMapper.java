package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.DTRequestDto;
import com.fdf.liga_mx.models.dtos.DTResponseDto;
import com.fdf.liga_mx.models.entitys.DT;
import com.fdf.liga_mx.models.entitys.Persona;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DTMapper {

    private final PersonaMapper personaMapper;

    public DT toEntity(DTRequestDto request) {
        if (request == null) {
            return null;
        }
        Persona persona = request.getIdPersona() != null ? Persona.builder().id(request.getIdPersona()).build() : null;

        return DT.builder()
                .id(request.getId())
                .tarjetasAmarillas(request.getTarjetasAmarillas())
                .tarjetasRojas(request.getTarjetasRojas())
                .idPersona(persona)
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
                .idPersona(personaMapper.toDto(entity.getIdPersona()))
                .build();
    }
}