package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.ArbitroRequest;
import com.fdf.liga_mx.models.dtos.response.ArbitroResponseDto;
import com.fdf.liga_mx.models.entitys.Arbitro;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArbitroServiceImpl implements IArbitroService {
    @Override
    public ArbitroResponseDto save(ArbitroRequest request) {
        return null;
    }

    @Override
    public List<Arbitro> findAll() {
        return null;
    }

    @Override
    public List<ArbitroResponseDto> findAllDto() {
        return null;
    }

    @Override
    public Arbitro findById(Long id) {
        return null;
    }

    @Override
    public ArbitroResponseDto findDtoById(Long id) {
        return null;
    }

    @Override
    public ArbitroResponseDto update(ArbitroRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
