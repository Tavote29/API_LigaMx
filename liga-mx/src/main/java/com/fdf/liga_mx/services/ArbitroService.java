package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.ArbitroMapper;
import com.fdf.liga_mx.models.entitys.Arbitro;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.repositories.ArbitroRepository;
import com.fdf.liga_mx.models.repositories.ICategoriaArbitroRepository;
import com.fdf.liga_mx.models.repositories.INacionalidadRepository;
import com.fdf.liga_mx.models.repositories.IStatusRepository;
import com.fdf.liga_mx.models.request.ArbitroRequest;
import com.fdf.liga_mx.models.response.ArbitroResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ArbitroService implements CrudService<ArbitroRequest, ArbitroResponse, Long>{
    private final INacionalidadRepository nacionalidadRepository;
    private final IStatusRepository statusRepository;
    private final ICategoriaArbitroRepository categoriaArbitroRepository;
    private final ArbitroRepository arbitroRepository;
    private final ArbitroMapper arbitroMapper;

    @Override
    public ArbitroResponse create(ArbitroRequest arbitroRequest) {
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
                .idPersona(persona)
                .idCategoriaArbitro(categoria)
                .build();

        var arbitroPersisted = this.arbitroRepository.save(arbitro);
        log.info("Arbitro creado con el ID: {}",arbitroPersisted.getId());
        return arbitroMapper.entityToResponse(arbitroPersisted);
    }

    @Override
    public ArbitroResponse read(Long aLong) {
        return null;
    }

    @Override
    public ArbitroResponse update(ArbitroRequest arbitroRequest, Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
