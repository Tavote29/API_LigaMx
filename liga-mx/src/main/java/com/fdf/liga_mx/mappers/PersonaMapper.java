package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.PersonaRequest;
import com.fdf.liga_mx.models.dtos.response.PersonaResponseDto;
import com.fdf.liga_mx.models.entitys.Nacionalidad;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.entitys.Status;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersonaMapper {

    private final StatusMapper statusMapper;
    private final NacionalidadMapper nacionalidadMapper;

    public Persona toEntity(PersonaRequest request) {
        if (request == null) {
            return null;
        }
        Status status = request.getIdStatus() != null ? Status.builder().id(request.getIdStatus()).build() : null;
        Nacionalidad nacionalidad = request.getIdNacionalidad() != null ? Nacionalidad.builder().id(request.getIdNacionalidad()).build() : null;

        return Persona.builder()
                .nombre(request.getNombre())
                .fechaNacimiento(request.getFechaNacimiento())
                .lugarNacimiento(request.getLugarNacimiento())
                .estatura(request.getEstatura())
                .peso(request.getPeso())
                .idStatus(status)
                .idNacionalidad(nacionalidad)
                .build();
    }

    public PersonaResponseDto toDto(Persona entity) {
        if (entity == null) {
            return null;
        }
        return PersonaResponseDto.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .fechaNacimiento(entity.getFechaNacimiento())
                .lugarNacimiento(entity.getLugarNacimiento())
                .estatura(entity.getEstatura())
                .peso(entity.getPeso())
                .idStatus(statusMapper.toDto(entity.getIdStatus()))
                .idNacionalidad(nacionalidadMapper.toDto(entity.getIdNacionalidad()))
                .build();
    }
}