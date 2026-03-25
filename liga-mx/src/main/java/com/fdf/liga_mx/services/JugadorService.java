package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.models.entitys.Jugador;

import java.util.List;

public class JugadorService implements IJugadorService{


    @Override
    public JugadorResponseDto save(JugadorRequest jugadorRequest) {
        return null;
    }

    @Override
    public List<Jugador> findAll() {
        return List.of();
    }

    @Override
    public List<JugadorResponseDto> findAllDto() {
        return List.of();
    }


    @Override
    public Jugador findById(Long aLong) {
        return null;
    }

    @Override
    public JugadorResponseDto findDtoById(Long aLong) {
        return null;
    }

    @Override
    public JugadorResponseDto update(JugadorRequest jugadorRequest) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
