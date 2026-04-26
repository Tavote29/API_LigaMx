package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.LesionRequestDto;
import com.fdf.liga_mx.models.dtos.response.LesionResponseDto;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Lesion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LesionMapper {

    private final JugadorMapper jugadorMapper;

    public Lesion toEntity(LesionRequestDto request){
        if(request == null){
            return null;
        }

        Jugador jugador = request.getNuiJugador() != null ? Jugador.builder().id(request.getNuiJugador()).build() : null;

        return Lesion.builder()
                .descripcionLesion(request.getDescripcionLesion())
                .jugador(jugador)
                .fechaLesion(request.getFechaLesion())
                .fechaRecuperacion(request.getFechaEstimadaRecuperacion())
                .build();
    }

    public LesionResponseDto toDto(Lesion entity){
        if (entity == null){
            return null;
        }
        return LesionResponseDto.builder()
                .idLesion(entity.getId())
                .descripcionLesion(entity.getDescripcionLesion())
                .fechaLesion(entity.getFechaLesion())
                .fechaRecuperacion(entity.getFechaRecuperacion())
                .jugador(jugadorMapper.toDto(entity.getJugador()))
                .build();
    }
}
