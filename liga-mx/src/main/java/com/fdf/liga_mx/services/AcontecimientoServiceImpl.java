package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.AcontecimientoRequest;
import com.fdf.liga_mx.models.dtos.response.AcontecimientoResponseDto;
import com.fdf.liga_mx.models.entitys.Acontecimiento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AcontecimientoServiceImpl implements IAcontecimientoService {
    @Override
    public AcontecimientoResponseDto save(AcontecimientoRequest request) {
        return null;
    }

    @Override
    public List<Acontecimiento> findAll() {
        return null;
    }

    @Override
    public List<AcontecimientoResponseDto> findAllDto() {
        return null;
    }

    @Override
    public Acontecimiento findById(UUID id) {
        return null;
    }

    @Override
    public AcontecimientoResponseDto findDtoById(UUID id) {
        return null;
    }

    @Override
    public AcontecimientoResponseDto update(AcontecimientoRequest request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
