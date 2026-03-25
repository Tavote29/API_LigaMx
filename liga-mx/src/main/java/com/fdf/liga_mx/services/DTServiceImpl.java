package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.models.entitys.DT;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTServiceImpl implements IDTService{

    @Override
    public DTResponseDto save(DTRequest dtRequest) {
        return null;
    }

    @Override
    public List<DT> findAll() {
        return List.of();
    }

    @Override
    public List<DTResponseDto> findAllDto() {
        return List.of();
    }

    @Override
    public DT findById(Long aLong) {
        return null;
    }

    @Override
    public DTResponseDto findDtoById(Long aLong) {
        return null;
    }

    @Override
    public DTResponseDto update(DTRequest dtRequest) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
