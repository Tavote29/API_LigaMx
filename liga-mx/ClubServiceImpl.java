package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.ClubMapper;
import com.fdf.liga_mx.models.dtos.request.ClubRequest;
import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.ClubResponseDto;
import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.repository.IClubRepository;
import com.fdf.liga_mx.repository.IEstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements IClubService{

    private final IClubRepository clubRepo;

    private final ClubMapper clubMapper;

    private final IEstadoRepository estadoRepo;


    @Override
    public ClubResponseDto changeStadium(EstadioRequestDto estadioRequestDto) {
        return null;
    }

    @Override
    public void assignDT(DTRequest dtRequest) {

    }

    @Override
    public List<ClubResponseDto> findByEstadoId(Short id) {
        return List.of();
    }

    @Override
    public List<ClubResponseDto> findByCiudadId(Short id) {
        return List.of();
    }

    @Override
    public ClubResponseDto save(ClubRequest clubRequest) {
        return null;
    }

    @Override
    public List<Club> findAll() {
        return clubRepo.findAll();
    }

    @Override
    public List<ClubResponseDto> findAllDto() {
        return clubRepo.findAll().stream().map(c -> clubMapper.toDto(c)).toList();
    }

    @Override
    public Club findById(Short id) {
        return clubRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Club no encontrado"));
    }

    @Override
    public ClubResponseDto findDtoById(Short id) {
        return clubMapper.toDto(clubRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Club no encontrado")));
    }

    @Override
    public ClubResponseDto update(ClubRequest request, Short id) {
        return null;
    }

    @Override
    public void delete(Short id) {

    }
}
