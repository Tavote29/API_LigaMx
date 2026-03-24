package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.JugadorRequestDto;
import com.fdf.liga_mx.models.dtos.JugadorResponseDto;
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

    public Jugador toEntity(JugadorRequestDto request) {
        if (request == null) {
            return null;
        }
        Persona persona = request.getIdPersona() != null ? Persona.builder().id(request.getIdPersona()).build() : null;
        Posicion posicion = request.getIdPosicion() != null ? Posicion.builder().id(request.getIdPosicion()).build() : null;
        Club club = request.getIdClub() != null ? Club.builder().id(request.getIdClub()).build() : null;

        return Jugador.builder()
                .id(request.getId())
                .tarjetasAmarillas(request.getTarjetasAmarillas())
                .tarjetasRojas(request.getTarjetasRojas())
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
}
