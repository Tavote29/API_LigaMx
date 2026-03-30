package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.request.ClubRequest;
import com.fdf.liga_mx.models.dtos.response.ClubResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Component
public class ClubMapper {

    private final EstadoMapper estadoMapper;
    private final CiudadMapper ciudadMapper;
    private final DTMapper dtMapper;
    private final EstadioMapper estadioMapper;
    private final JugadorMapper jugadorMapper;

    @Autowired
    public ClubMapper(EstadoMapper estadoMapper, CiudadMapper ciudadMapper, DTMapper dtMapper, EstadioMapper estadioMapper, @Lazy JugadorMapper jugadorMapper) {
        this.estadoMapper = estadoMapper;
        this.ciudadMapper = ciudadMapper;
        this.dtMapper = dtMapper;
        this.estadioMapper = estadioMapper;
        this.jugadorMapper = jugadorMapper;
    }

    public Club toEntity(ClubRequest request) {
        if (request == null) {
            return null;
        }
        Estado estado = request.getIdEstado() != null ? Estado.builder().id(request.getIdEstado()).build() : null;
        Ciudad ciudad = request.getIdCiudad() != null ? Ciudad.builder().id(request.getIdCiudad()).build() : null;
        DT dt = request.getIdDt() != null ? DT.builder().id(request.getIdDt()).build() : null;
        Estadio estadio = request.getIdEstadio() != null ? Estadio.builder().id(request.getIdEstadio()).build() : null;

        return Club.builder()
                .nombreClub(request.getNombreClub())
                .fechaFundacion(request.getFechaFundacion())
                .propietario(request.getPropietario())
                .idEstado(estado)
                .idCiudad(ciudad)
                .idDt(dt)
                .idEstadio(estadio)
                .build();
    }

    public ClubResponseDto toDto(Club entity) {
        if (entity == null) {
            return null;
        }
        return ClubResponseDto.builder()
                .id(entity.getId())
                .nombreClub(entity.getNombreClub())
                .fechaFundacion(entity.getFechaFundacion())
                .propietario(entity.getPropietario())
                .idEstado(estadoMapper.toDto(entity.getIdEstado()))
                .idCiudad(ciudadMapper.toDto(entity.getIdCiudad()))
                .idDt(dtMapper.toDto(entity.getIdDt()))
                .idEstadio(estadioMapper.toDto(entity.getIdEstadio()))
                .jugadores(entity.getJugadores().stream().map(j -> jugadorMapper.toDtoSinClub(j)).toList())
                .build();
    }

    public Club updateEntity(ClubRequest request, Club entity) {
        if (request == null) {
            return null;
        }
        Estado estado = request.getIdEstado() != null ? Estado.builder().id(request.getIdEstado()).build() : null;
        Ciudad ciudad = request.getIdCiudad() != null ? Ciudad.builder().id(request.getIdCiudad()).build() : null;

        if (!entity.getNombreClub().equals(request.getNombreClub())) {
            entity.setNombreClub(request.getNombreClub());
        }
        if (!entity.getFechaFundacion().equals(request.getFechaFundacion())) {
            entity.setFechaFundacion(request.getFechaFundacion());

        }
        if (!entity.getPropietario().equals(request.getPropietario())) {
            entity.setPropietario(request.getPropietario());
        }
        if (!entity.getIdEstado().equals(estado)) {
            entity.setIdEstado(estado);
        }
        if (!entity.getIdCiudad().equals(ciudad)) {
            entity.setIdCiudad(ciudad);
        }

        return entity;
    }
}
