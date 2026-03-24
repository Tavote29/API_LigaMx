package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.ArbitroRequestDto;
import com.fdf.liga_mx.models.dtos.ArbitroResponseDto;
import com.fdf.liga_mx.models.entitys.Arbitro;
import com.fdf.liga_mx.models.entitys.CategoriaArbitro;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.entitys.CategoriaArbitro;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArbitroMapper {

    private final PersonaMapper personaMapper;
    private final CategoriaArbitroMapper categoriaMapper;

    public Arbitro toEntity(ArbitroRequestDto request) {
        if (request == null) {
            return null;
        }
        Persona persona = request.getIdPersona() != null ? Persona.builder().id(request.getIdPersona()).build() : null;
        CategoriaArbitro categoria = request.getIdCategoria() != null ? CategoriaArbitro.builder().id(request.getIdCategoria()).build() : null;

        return Arbitro.builder()
                .id(request.getId())
                .idPersona(persona)
                .fechaIncorporacion(request.getFechaIncorporacion())
                .idCategoriaArbitro(categoria)
                .build();
    }

    public ArbitroResponseDto toDto(Arbitro entity) {
        if (entity == null) {
            return null;
        }
        return ArbitroResponseDto.builder()
                .id(entity.getId())
                .idPersona(personaMapper.toDto(entity.getIdPersona()))
                .fechaIncorporacion(entity.getFechaIncorporacion())
                .idCategoria(categoriaMapper.toDto(entity.getIdCategoriaArbitro()))
                .build();
    }
}