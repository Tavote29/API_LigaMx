package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.ArbitroRequest;
import com.fdf.liga_mx.models.dtos.response.ArbitroResponseDto;
import com.fdf.liga_mx.models.entitys.Arbitro;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IArbitroService extends CrudService<ArbitroRequest, ArbitroResponseDto, Arbitro, Long> {
    Page<ArbitroResponseDto> searchArbitro(Integer page, Integer size, String sorts, String nombre, Integer nacionalidad, Short categoria);
    ArbitroResponseDto save(ArbitroRequest arbitroRequest, MultipartFile file) throws IOException;
    void delete(Long id);
}
