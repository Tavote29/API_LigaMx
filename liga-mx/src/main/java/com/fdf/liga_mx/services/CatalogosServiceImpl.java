package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.response.*;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.models.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CatalogosServiceImpl implements ICatalogosService{

    private final ICategoriaArbitroRepository categoriaArbitroRepo;

    private final INacionalidadRepository nacionalidadRepo;

    private final IPosicionRepository posicionRepo;

    private final IStatusRepository statusRepo;

    private final ITiposAcontecimientoRepository tiposAcontecimientoRepo;

    private final CategoriaArbitroMapper categoriaArbitroMapper;

    private final NacionalidadMapper nacionalidadMapper;

    private final PosicionMapper posicionMapper;

    private final StatusMapper statusMapper;

    private final TiposAcontecimientoMapper tiposAcontecimientoMapper;


    public CatalogosServiceImpl(ICategoriaArbitroRepository categoriaArbitroRepo, INacionalidadRepository nacionalidadRepo, IPosicionRepository posicionRepo, IStatusRepository statusRepo, ITiposAcontecimientoRepository tiposAcontecimientoRepo, CategoriaArbitroMapper categoriaArbitroMapper, NacionalidadMapper nacionalidadMapper, PosicionMapper posicionMapper, StatusMapper statusMapper, TiposAcontecimientoMapper tiposAcontecimientoMapper) {
        this.categoriaArbitroRepo = categoriaArbitroRepo;
        this.nacionalidadRepo = nacionalidadRepo;
        this.posicionRepo = posicionRepo;
        this.statusRepo = statusRepo;
        this.tiposAcontecimientoRepo = tiposAcontecimientoRepo;
        this.categoriaArbitroMapper = categoriaArbitroMapper;
        this.nacionalidadMapper = nacionalidadMapper;
        this.posicionMapper = posicionMapper;
        this.statusMapper = statusMapper;
        this.tiposAcontecimientoMapper = tiposAcontecimientoMapper;
    }

    @Override
    public List<CategoriaArbitroResponseDto> findAllCategoriasArbitros() {

        return categoriaArbitroRepo.findAll().stream().map(c -> categoriaArbitroMapper.toDto(c)).toList();

    }

    @Override
    public List<NacionalidadResponseDto> findAllNacionalidades() {
        return nacionalidadRepo.findAll().stream().map(n -> nacionalidadMapper.toDto(n)).toList();
    }

    @Override
    public List<PosicionResponseDto> findAllPosiciones() {
        return posicionRepo.findAll().stream().map(p -> posicionMapper.toDto(p)).toList();
    }

    @Override
    public List<StatusResponseDto> findAllStatuses() {
        return statusRepo.findAll().stream().map(s -> statusMapper.toDto(s)).toList();
    }

    @Override
    public List<TiposAcontecimientoResponseDto> findAllTiposAcontecimientos() {
        return tiposAcontecimientoRepo.findAll().stream().map(t -> tiposAcontecimientoMapper.toDto(t)).toList();
    }

    @Override
    public CategoriaArbitroResponseDto findCategoriaArbitroById(Short id) {

        CategoriaArbitro categoriaArbitro = categoriaArbitroRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro la categoria"));

        return categoriaArbitroMapper.toDto(categoriaArbitro);
    }

    @Override
    public NacionalidadResponseDto findNacionalidadById(Short id) {

        Nacionalidad nacionalidad = nacionalidadRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro la nacionalidad"));

        return nacionalidadMapper.toDto(nacionalidad);

    }

    @Override
    public PosicionResponseDto findPosicionById(Short id) {

        Posicion posicion = posicionRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro la posicion"));

        return posicionMapper.toDto(posicion);
    }

    @Override
    public StatusResponseDto findStatusById(Short id) {

        Status status = statusRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el status"));

        return statusMapper.toDto(status);
    }

    @Override
    public TiposAcontecimientoResponseDto findTipoAcontecimientoById(Short id) {

        TiposAcontecimiento tiposAcontecimiento = tiposAcontecimientoRepo
                .findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro la tiposAcontecimiento"));

        return tiposAcontecimientoMapper.toDto(tiposAcontecimiento);
    }
}
