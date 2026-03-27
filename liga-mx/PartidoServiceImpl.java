package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.PartidoRequest;
import com.fdf.liga_mx.models.dtos.response.PartidoResponseDto;
import com.fdf.liga_mx.models.entitys.Partido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PartidoServiceImpl implements IPartidoService {
    @Override
    public PartidoResponseDto save(PartidoRequest request) {
        return null;
    }

    @Override
    public List<Partido> findAll() {
        return null;
    }

    @Override
    public List<PartidoResponseDto> findAllDto() {
        return null;
    }

    @Override
    public Partido findById(UUID id) {
        return null;
    }

    @Override
    public PartidoResponseDto findDtoById(UUID id) {
        return null;
    }

    @Override
    public PartidoResponseDto update(PartidoRequest request, UUID id) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
