package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.models.entitys.DT;
import org.springframework.data.domain.Page;

public interface IDTService extends  CrudService<DTRequest, DTResponseDto, DT, Long>{
    Page<DTResponseDto> searchDT(Integer page, Integer size, String sorts, String nombre, Integer nacionalidad, Short club);
}
