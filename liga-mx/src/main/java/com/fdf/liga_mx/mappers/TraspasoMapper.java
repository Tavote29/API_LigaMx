package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.TraspasoRequestDTO;
import com.fdf.liga_mx.models.dtos.response.TraspasoResponseDto;
import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.TipoTraspaso;
import com.fdf.liga_mx.models.entitys.Traspaso;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraspasoMapper {
    private final JugadorMapper jugadorMapper;
    private final ClubMapper clubMapper;
    private final TipoTraspasoMapper tipoTraspasoMapper;

    public Traspaso toEntity(TraspasoRequestDTO request){
        if(request == null){
            return null;
        }
        TipoTraspaso tipoTraspaso = request.getTipo() != null ? TipoTraspaso.builder().id(request.getTipo()).build() : null;
        Jugador jugador = request.getNuiJugador() != null ? Jugador.builder().id(request.getNuiJugador()).build(): null;
        Club clubOrigen = request.getClubOrigen() != null ? Club.builder().id(request.getClubOrigen()).build() : null;
        Club clubDestino = request.getClubDestino() != null ? Club.builder().id(request.getClubDestino()).build() : null;

        return Traspaso.builder()
                .jugador(jugador)
                .clubOrigen(clubOrigen)
                .clubDestino(clubDestino)
                .fechaTraspaso(request.getFechaTraspaso())
                .fechaFin(request.getFechaFin())
                .valor(request.getValor())
                .tipo(tipoTraspaso)
                .build();
    }

    public TraspasoResponseDto toDto(Traspaso entity){
        if (entity == null){
            return null;
        }
        return TraspasoResponseDto.builder()
                .uuid(entity.getId())
                .nuiJugador(jugadorMapper.toDto(entity.getJugador()))
                .clubOrigen(clubMapper.toDto(entity.getClubOrigen()))
                .clubDestino(clubMapper.toDto(entity.getClubDestino()))
                .fechaTraspaso(entity.getFechaTraspaso())
                .fechaFin(entity.getFechaFin())
                .tipo(tipoTraspasoMapper.toDto(entity.getTipo()))
                .valor(entity.getValor())
                .build();
    }
}
