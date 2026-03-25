package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.models.entitys.DT;

public interface IDTService extends  CrudService<DTRequest, DTResponseDto, DT, Long>{
}
