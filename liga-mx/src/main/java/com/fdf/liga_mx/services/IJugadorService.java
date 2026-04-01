package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.models.entitys.Jugador;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface IJugadorService extends CrudService<JugadorRequest, JugadorResponseDto, Jugador, Long>{
    Page<JugadorResponseDto> searchJugador(Integer page, Integer size, String sorts, String nombre, Integer nacionalidad, Short club);
    Map<String,Integer> obtenerTarjetasJugadorPorTorneoId(Long jugadorId, Long torneoId);
}
