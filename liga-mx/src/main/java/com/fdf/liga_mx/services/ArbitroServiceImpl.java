package com.fdf.liga_mx.services;


import com.fdf.liga_mx.mappers.ArbitroMapper;
import com.fdf.liga_mx.mappers.PersonaMapper;
import com.fdf.liga_mx.models.dtos.request.ArbitroRequest;
import com.fdf.liga_mx.models.dtos.response.ArbitroResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.repository.ArbitroRepository;
import com.fdf.liga_mx.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ArbitroServiceImpl implements IArbitroService {

    private final ArbitroRepository arbitroRepository;
    private final PersonaMapper personaMapper;
    private final ArbitroMapper arbitroMapper;
    private final ICatalogosService catalogosService;
    private final MediaStorageService mediaService;

    @Override
    @Transactional
    public ArbitroResponseDto save(ArbitroRequest request) {
        CategoriaArbitro categoriaArbitro = catalogosService.findCategoriaArbitroEntityById(request.getIdCategoria());
        Persona persona = personaMapper.toEntity(request.getPersona());
        Arbitro arbitro = arbitroMapper.toEntity(request);
        arbitro.setPersona(persona);
        arbitro.setIdCategoriaArbitro(categoriaArbitro);
        return arbitroMapper.toDto(arbitroRepository.saveAndFlush(arbitro));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Arbitro> findAll() {
        return arbitroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArbitroResponseDto> findAllDto() {
        return arbitroRepository.findAll().stream().map(arbitro -> arbitroMapper.toDto(arbitro)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Arbitro findById(Long id) {
        return arbitroRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro el arbitro indicado"));
    }

    @Override
    @Transactional(readOnly = true)
    public ArbitroResponseDto findDtoById(Long id) {
        Arbitro arbitro = arbitroRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro el arbitro indicado"));
        return arbitroMapper.toDto(arbitro);
    }

    @Override
    @Transactional
    public ArbitroResponseDto update(ArbitroRequest request, Long id) {
        Arbitro arbitro = arbitroRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro el arbitro indicado"));
        Nacionalidad nacionalidad = catalogosService.findNacionalidadEntityById(request.getPersona().getIdNacionalidad());
        Status status = catalogosService.findStatusEntityById(request.getPersona().getIdStatus());

        if (!arbitro.getIdCategoriaArbitro().getId().equals(request.getIdCategoria())){
            CategoriaArbitro categoriaArbitro = catalogosService.findCategoriaArbitroEntityById(request.getIdCategoria());
            arbitro.setIdCategoriaArbitro(categoriaArbitro);
        }

        personaMapper.updateEntity(arbitro.getPersona(),request.getPersona(),nacionalidad,status);

        return arbitroMapper.toDto(arbitroRepository.saveAndFlush(arbitro));
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<ArbitroResponseDto> searchArbitro(Integer page, Integer size, String sorts, String nombre, Integer nacionalidad, Short categoria) {
        Pageable pageable = PageRequest.of(page,size, Utils.parseSortParams(sorts));
        Page<Arbitro> arbitroPage = arbitroRepository.searchArbitro(pageable,nombre,nacionalidad,categoria);
        return arbitroPage.map(arbitro -> arbitroMapper.toDto(arbitro));
    }

    @Override
    @Transactional
    public ArbitroResponseDto save(ArbitroRequest arbitroRequest, MultipartFile file) throws IOException {
        CategoriaArbitro categoriaArbitro = catalogosService.findCategoriaArbitroEntityById(arbitroRequest.getIdCategoria());

        Persona persona = personaMapper.toEntity(arbitroRequest.getPersona());
        Arbitro arbitro = arbitroMapper.toEntity(arbitroRequest);
        arbitro.setPersona(persona);
        arbitro.setIdCategoriaArbitro(categoriaArbitro);

        Arbitro arbitroSaved = arbitroRepository.saveAndFlush(arbitro);

        if (file == null || file.isEmpty())
            return arbitroMapper.toDto(arbitroSaved);

        String storageKey = mediaService.uploadFile(file, arbitroSaved.getPersona().getId().toString());
        arbitroSaved.getPersona().setImageUrl(storageKey);

        return arbitroMapper.toDto(arbitroRepository.save(arbitroSaved));
    }
}
