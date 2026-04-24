package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.AcontecimientoRequest;
import com.fdf.liga_mx.models.dtos.response.AcontecimientoResponseDto;
import com.fdf.liga_mx.models.entitys.Acontecimiento;
import com.fdf.liga_mx.models.entitys.Partido;

import java.util.UUID;

public interface IAcontecimientoService extends CrudService<AcontecimientoRequest, AcontecimientoResponseDto, Acontecimiento, UUID> {
    void verificacionCambioValido(AcontecimientoRequest request, Partido partido);
}
