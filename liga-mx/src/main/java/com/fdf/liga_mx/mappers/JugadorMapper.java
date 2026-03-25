package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.entitys.Posicion;
import com.fdf.liga_mx.models.entitys.Club;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JugadorMapper {

    private final PersonaMapper personaMapper;
    private final PosicionMapper posicionMapper;
    private final ClubMapper clubMapper;

    public Jugador toEntity(JugadorRequest request) {

        if (request == null) {
            return null;
        }

        Persona persona = request.getPersona() != null ? personaMapper.toEntity(request.getPersona()) : null;
        Posicion posicion = request.getId_posicion() != null ? Posicion.builder().id(request.getId_posicion()).build() : null;
        Club club = request.getId_club() != null ? Club.builder().id(request.getId_club()).build() : null;

        return Jugador.builder()
                .dorsal(request.getDorsal())
                .idPersona(persona)
                .idPosicion(posicion)
                .idClub(club)
                .build();
    }

    public JugadorResponseDto toDto(Jugador entity) {
        if (entity == null) {
            return null;
        }
        return JugadorResponseDto.builder()
                .id(entity.getId())
                .tarjetasAmarillas(entity.getTarjetasAmarillas())
                .tarjetasRojas(entity.getTarjetasRojas())
                .dorsal(entity.getDorsal())
                .idPersona(personaMapper.toDto(entity.getIdPersona()))
                .idPosicion(posicionMapper.toDto(entity.getIdPosicion()))
                .idClub(clubMapper.toDto(entity.getIdClub()))
                .build();
    }

    public JugadorResponseDto toDtoSinClub(Jugador entity) {
        if (entity == null) {
            return null;
        }
        return JugadorResponseDto.builder()
                .id(entity.getId())
                .tarjetasAmarillas(entity.getTarjetasAmarillas())
                .tarjetasRojas(entity.getTarjetasRojas())
                .dorsal(entity.getDorsal())
                .idPersona(personaMapper.toDto(entity.getIdPersona()))
                .idPosicion(posicionMapper.toDto(entity.getIdPosicion()))
                .build();
    }

}
