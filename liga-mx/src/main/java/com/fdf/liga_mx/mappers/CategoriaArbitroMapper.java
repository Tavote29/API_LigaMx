package com.fdf.liga_mx.mappers;


import com.fdf.liga_mx.models.dtos.CategoriaArbitroRequestDto;
import com.fdf.liga_mx.models.dtos.CategoriaArbitroResponseDto;
import com.fdf.liga_mx.models.entitys.CategoriaArbitro;
import org.springframework.stereotype.Component;


@Component
public class CategoriaArbitroMapper {

    public CategoriaArbitro toEntity(CategoriaArbitroRequestDto request){
        if (request == null) {
            return null;
        }
        return CategoriaArbitro.builder()
                .id(request.getId())
                .descripcionCategoria(request.getDescripcionCategoria()).build();
    }

    public CategoriaArbitroResponseDto toDto(CategoriaArbitro entity) {
        if (entity == null) {
            return null;
        }
        return CategoriaArbitroResponseDto.builder()
                .id(entity.getId())
                .descripcionCategoria(entity.getDescripcionCategoria())
                .build();
    }
}