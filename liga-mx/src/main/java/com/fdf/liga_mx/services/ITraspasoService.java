package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.TraspasoRequestDTO;
import com.fdf.liga_mx.models.dtos.response.TraspasoResponseDto;
import com.fdf.liga_mx.models.entitys.Traspaso;

import java.util.UUID;

public interface ITraspasoService extends CrudService<TraspasoRequestDTO, TraspasoResponseDto, Traspaso, UUID>{
}
