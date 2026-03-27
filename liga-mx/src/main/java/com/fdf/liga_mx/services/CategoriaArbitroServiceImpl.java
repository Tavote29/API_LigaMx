package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.CategoriaArbitroRequestDto;
import com.fdf.liga_mx.models.dtos.response.CategoriaArbitroResponseDto;
import com.fdf.liga_mx.models.entitys.CategoriaArbitro;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaArbitroServiceImpl implements ICategoriaArbitroService {
    @Override
    public CategoriaArbitroResponseDto save(CategoriaArbitroRequestDto request) {
        return null;
    }

    @Override
    public List<CategoriaArbitro> findAll() {
        return null;
    }

    @Override
    public List<CategoriaArbitroResponseDto> findAllDto() {
        return null;
    }

    @Override
    public CategoriaArbitro findById(Short id) {
        return null;
    }

    @Override
    public CategoriaArbitroResponseDto findDtoById(Short id) {
        return null;
    }

    @Override
    public CategoriaArbitroResponseDto update(CategoriaArbitroRequestDto request, Short id) {
        return null;
    }

    @Override
    public void delete(Short id) {

    }
}
