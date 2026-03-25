package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.EstadioResponseDto;
import com.fdf.liga_mx.models.entitys.Estadio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadioServiceImpl implements IEstadioService {
    @Override
    public EstadioResponseDto save(EstadioRequestDto request) {
        return null;
    }

    @Override
    public List<Estadio> findAll() {
        return null;
    }

    @Override
    public List<EstadioResponseDto> findAllDto() {
        return null;
    }

    @Override
    public Estadio findById(Short id) {
        return null;
    }

    @Override
    public EstadioResponseDto findDtoById(Short id) {
        return null;
    }

    @Override
    public EstadioResponseDto update(EstadioRequestDto request) {
        return null;
    }

    @Override
    public void delete(Short id) {

    }
}
