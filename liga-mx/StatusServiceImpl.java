package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.StatusRequestDto;
import com.fdf.liga_mx.models.dtos.response.StatusResponseDto;
import com.fdf.liga_mx.models.entitys.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements IStatusService {
    @Override
    public StatusResponseDto save(StatusRequestDto request) {
        return null;
    }

    @Override
    public List<Status> findAll() {
        return null;
    }

    @Override
    public List<StatusResponseDto> findAllDto() {
        return null;
    }

    @Override
    public Status findById(Short id) {
        return null;
    }

    @Override
    public StatusResponseDto findDtoById(Short id) {
        return null;
    }

    @Override
    public StatusResponseDto update(StatusRequestDto request, Short id) {
        return null;
    }

    @Override
    public void delete(Short id) {

    }
}
