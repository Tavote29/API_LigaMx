package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.AcontecimientoRequest;
import com.fdf.liga_mx.models.dtos.response.AcontecimientoResponseDto;
import com.fdf.liga_mx.models.entitys.Acontecimiento;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Partido;
import com.fdf.liga_mx.models.entitys.TiposAcontecimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcontecimientoMapper {

    private final TiposAcontecimientoMapper tiposAcontecimientoMapper;
    private final JugadorMapper jugadorMapper;
    private final PartidoMapper partidoMapper;

    public Acontecimiento toEntity(AcontecimientoRequest request) {
        if (request == null) {
            return null;
        }
        TiposAcontecimiento tipo = request.getIdTipo() != null ? TiposAcontecimiento.builder().id(request.getIdTipo()).build() : null;
        Jugador primario = request.getIdJugadorPrimario() != null ? Jugador.builder().id(request.getIdJugadorPrimario()).build() : null;
        Jugador secundario = request.getIdJugadorSecundario() != null ? Jugador.builder().id(request.getIdJugadorSecundario()).build() : null;
        Partido partido = request.getIdPartido() != null ? Partido.builder().id(request.getIdPartido()).build() : null;

        return Acontecimiento.builder()
                .idTipo(tipo)
                .minuto(request.getMinuto())
                .idJugadorPrimario(primario)
                .idJugadorSecundario(secundario)
                .idPartido(partido)
                .build();
    }

    public AcontecimientoResponseDto toDto(Acontecimiento entity) {
        if (entity == null) {
            return null;
        }
        return AcontecimientoResponseDto.builder()
                .id(entity.getId())
                .idTipo(tiposAcontecimientoMapper.toDto(entity.getIdTipo()))
                .minuto(entity.getMinuto())
                .idJugadorPrimario(jugadorMapper.toDtoSinClub(entity.getIdJugadorPrimario()))
                .idJugadorSecundario(jugadorMapper.toDtoSinClub(entity.getIdJugadorSecundario()))
                .idPartido(partidoMapper.toDto(entity.getIdPartido()))
                .build();
    }
}