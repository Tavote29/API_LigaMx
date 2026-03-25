package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.models.entitys.Jugador;

public interface IJugadorService extends CrudService<JugadorRequest, JugadorResponseDto, Jugador, Long>{

}
