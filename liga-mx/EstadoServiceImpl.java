package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.EstadoRequestDto;
import com.fdf.liga_mx.models.dtos.response.EstadoResponseDto;
import com.fdf.liga_mx.models.entitys.Estado;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoServiceImpl implements IEstadoService {
    @Override
    public EstadoResponseDto save(EstadoRequestDto request) {
        return null;
    }

    @Override
    public List<Estado> findAll() {
        return null;
    }

    @Override
    public List<EstadoResponseDto> findAllDto() {
        return null;
    }

    @Override
    public Estado findById(Short id) {
        return null;
    }

    @Override
    public EstadoResponseDto findDtoById(Short id) {
        return null;
    }

    @Override
    public EstadoResponseDto update(EstadoRequestDto request, Short id) {
        return null;
    }

    @Override
    public void delete(Short id) {

    }
}
