package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.ArbitroRequest;
import com.fdf.liga_mx.models.dtos.response.ArbitroResponseDto;
import com.fdf.liga_mx.models.entitys.Arbitro;

public interface IArbitroService extends CrudService<ArbitroRequest, ArbitroResponseDto, Arbitro, Long> {
}
