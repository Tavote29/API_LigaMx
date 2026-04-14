package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.models.entitys.DT;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IDTService extends  CrudService<DTRequest, DTResponseDto, DT, Long>{
    Page<DTResponseDto> searchDT(Integer page, Integer size, String sorts, String nombre, Integer nacionalidad, Short club);
    DTResponseDto save(DTRequest dtRequest, MultipartFile file) throws IOException;

    void liberarDt(Long id);
}
