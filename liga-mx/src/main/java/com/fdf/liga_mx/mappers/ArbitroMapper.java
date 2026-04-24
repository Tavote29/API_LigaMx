package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.ArbitroRequest;
import com.fdf.liga_mx.models.dtos.response.ArbitroResponseDto;
import com.fdf.liga_mx.models.entitys.Arbitro;
import com.fdf.liga_mx.models.entitys.CategoriaArbitro;
import com.fdf.liga_mx.models.entitys.Persona;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArbitroMapper {

    private final PersonaMapper personaMapper;
    private final CategoriaArbitroMapper categoriaMapper;

    public Arbitro toEntity(ArbitroRequest request) {
        if (request == null) {
            return null;
        }
        Persona persona = request.getPersona() != null ? personaMapper.toEntity(request.getPersona()) : null;
        CategoriaArbitro categoria = request.getIdCategoria() != null ? CategoriaArbitro.builder().id(request.getIdCategoria()).build() : null;

        return Arbitro.builder()

                .persona(persona)
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
                .idPersona(personaMapper.toDto(entity.getPersona()))
                .fechaIncorporacion(entity.getFechaIncorporacion())
                .idCategoria(categoriaMapper.toDto(entity.getIdCategoriaArbitro()))
                .build();
    }
}
