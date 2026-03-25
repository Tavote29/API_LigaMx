package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.PosicionRequestDto;
import com.fdf.liga_mx.models.dtos.response.PosicionResponseDto;
import com.fdf.liga_mx.models.entitys.Posicion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosicionServiceImpl implements IPosicionService {
    @Override
    public PosicionResponseDto save(PosicionRequestDto request) {
        return null;
    }

    @Override
    public List<Posicion> findAll() {
        return null;
    }

    @Override
    public List<PosicionResponseDto> findAllDto() {
        return null;
    }

    @Override
    public Posicion findById(Short id) {
        return null;
    }

    @Override
    public PosicionResponseDto findDtoById(Short id) {
        return null;
    }

    @Override
    public PosicionResponseDto update(PosicionRequestDto request) {
        return null;
    }

    @Override
    public void delete(Short id) {

    }
}
