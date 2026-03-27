package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.ArbitroMapper;
import com.fdf.liga_mx.models.dtos.request.ArbitroRequest;
import com.fdf.liga_mx.models.dtos.response.ArbitroResponseDto;
import com.fdf.liga_mx.models.entitys.Arbitro;
import com.fdf.liga_mx.models.entitys.Persona;

import com.fdf.liga_mx.models.repositories.ICategoriaArbitroRepository;
import com.fdf.liga_mx.models.repositories.INacionalidadRepository;
import com.fdf.liga_mx.models.repositories.IStatusRepository;


import com.fdf.liga_mx.repository.ArbitroRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ArbitroService implements IArbitroService{
    private final INacionalidadRepository nacionalidadRepository;
    private final IStatusRepository statusRepository;
    private final ICategoriaArbitroRepository categoriaArbitroRepository;
    private final ArbitroRepository arbitroRepository;
    private final ArbitroMapper arbitroMapper;



    @Override
    public ArbitroResponseDto save(ArbitroRequest arbitroRequest) {
        var nacionalidad = nacionalidadRepository.findById(arbitroRequest.getPersona().getIdNacionalidad()).orElseThrow();
        var status = statusRepository.findById(arbitroRequest.getPersona().getIdStatus()).orElseThrow();
        var categoria = categoriaArbitroRepository.findById(arbitroRequest.getIdCategoria()).orElseThrow();

        var persona = Persona.builder()
                .id(UUID.randomUUID())
                .nombre(arbitroRequest.getPersona().getNombre())
                .fechaNacimiento(arbitroRequest.getPersona().getFechaNacimiento())
                .estatura(arbitroRequest.getPersona().getEstatura())
                .peso(arbitroRequest.getPersona().getPeso())
                .idStatus(status)
                .idNacionalidad(nacionalidad)
                .build();

        var arbitro = Arbitro.builder()
                .fechaIncorporacion(arbitroRequest.getFechaIncorporacion())
                .persona(persona)
                .idCategoriaArbitro(categoria)
                .build();

        var arbitroPersisted = this.arbitroRepository.save(arbitro);
        log.info("Arbitro creado con el ID: {}",arbitroPersisted.getId());
        return arbitroMapper.toDto(arbitroPersisted);
    }

    @Override
    public List<Arbitro> findAll() {
        return List.of();
    }

    @Override
    public List<ArbitroResponseDto> findAllDto() {
        return List.of();
    }

    @Override
    public Arbitro findById(Long aLong) {
        return null;
    }

    @Override
    public ArbitroResponseDto findDtoById(Long aLong) {
        return null;
    }

    @Override
    public ArbitroResponseDto update(ArbitroRequest arbitroRequest, Long id) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
