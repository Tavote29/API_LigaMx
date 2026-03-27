package com.fdf.liga_mx.services;


import com.fdf.liga_mx.mappers.EstadioMapper;
import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.EstadioResponseDto;
import com.fdf.liga_mx.models.entitys.Ciudad;
import com.fdf.liga_mx.models.entitys.Estadio;
import com.fdf.liga_mx.models.entitys.Estado;
import com.fdf.liga_mx.repository.EstadioRepository;
import com.fdf.liga_mx.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class EstadioServiceImpl implements IEstadioService {

    private final EstadioRepository estadioRepo;

    private final EstadioMapper estadioMapper;

    private final ICatalogosService catalogosService;

    @Override
    @Transactional
    public EstadioResponseDto save(EstadioRequestDto request) {

        Estado estado = catalogosService.findEstadoById(request.getIdEstado());

        Ciudad ciudad = catalogosService.findCiudadById(request.getIdCiudad());

        Estadio estadio = estadioMapper.toEntity(request);

        estadio.setIdEstado(estado);
        estadio.setIdCiudad(ciudad);

        return estadioMapper.toDto(estadioRepo.saveAndFlush(estadio));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estadio> findAll() {
        return estadioRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadioResponseDto> findAllDto() {

        return estadioRepo.findAll().stream().map(e -> estadioMapper.toDto(e)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Estadio findById(Short id) {
        return estadioRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el estadio"));
    }

    @Override
    @Transactional(readOnly = true)
    public EstadioResponseDto findDtoById(Short id) {

        Estadio estadio = estadioRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el estadio"));

        return estadioMapper.toDto(estadio);
    }

    @Override
    @Transactional
    public EstadioResponseDto update(EstadioRequestDto request, Short id) {

        Estadio estadio = estadioRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el estadio"));

        if (!estadio.getNombreEstadio().equals(request.getNombreEstadio())){
            estadio.setNombreEstadio(request.getNombreEstadio());

        }

        if (!estadio.getDireccion().equals(request.getDireccion())){
            estadio.setDireccion(request.getDireccion());
        }

        if (!estadio.getCapacidad().equals(request.getCapacidad())){

            estadio.setCapacidad(request.getCapacidad());
        }

        if (!estadio.getIdEstado().getId().equals(request.getIdEstado())){
            Estado estado = catalogosService.findEstadoById(request.getIdEstado());
            estadio.setIdEstado(estado);
        }

        if (!estadio.getIdCiudad().getId().equals(request.getIdCiudad())){
            Ciudad ciudad = catalogosService.findCiudadById(request.getIdCiudad());
            estadio.setIdCiudad(ciudad);
        }


        return estadioMapper.toDto(estadioRepo.saveAndFlush(estadio));

    }

    @Override
    @Transactional
    public void delete(Short id) {

        Estadio estadio = estadioRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el estadio"));

        //PENDIENTE IMPLEMENTACION SOFT DELETE

    }

    @Override
    public Page<EstadioResponseDto> searchStadium(Integer page,
                                                  Integer size,
                                                  String sorts,
                                                  Short ciudadId,
                                                  Short estadoId,
                                                  String nombre,
                                                  Integer minCapacity,
                                                  Integer maxCapacity) {

        Pageable pageable = PageRequest.of(page, size, Utils.parseSortParams(sorts));


        Page<Estadio> pageEstadios = estadioRepo.searchEstadio(pageable, ciudadId, estadoId, nombre, minCapacity, maxCapacity);


        return pageEstadios.map(e -> estadioMapper.toDto(e));
    }
}
