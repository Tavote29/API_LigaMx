package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.models.entitys.Jugador;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public interface IJugadorService extends CrudService<JugadorRequest, JugadorResponseDto, Jugador, Long>{
    Page<JugadorResponseDto> searchJugador(Integer page, Integer size, String sorts, String nombre, Integer nacionalidad, Short club);
    Map<String,Integer> obtenerTarjetasJugadorPorTorneoId(Long jugadorId, Long torneoId);

    void updateTarjetasByPartidoId(UUID id);

    JugadorResponseDto save(JugadorRequest jugadorRequest, MultipartFile file) throws IOException;
}
