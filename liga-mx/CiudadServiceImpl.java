package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.CiudadRequestDto;
import com.fdf.liga_mx.models.dtos.response.CiudadResponseDto;
import com.fdf.liga_mx.models.entitys.Ciudad;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadServiceImpl implements ICiudadService {
    @Override
    public CiudadResponseDto save(CiudadRequestDto request) {
        return null;
    }

    @Override
    public List<Ciudad> findAll() {
        return null;
    }

    @Override
    public List<CiudadResponseDto> findAllDto() {
        return null;
    }

    @Override
    public Ciudad findById(Short id) {
        return null;
    }

    @Override
    public CiudadResponseDto findDtoById(Short id) {
        return null;
    }

    @Override
    public CiudadResponseDto update(CiudadRequestDto request, Short id) {
        return null;
    }

    @Override
    public void delete(Short id) {

    }
}
