package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.response.*;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.models.repositories.*;
import com.fdf.liga_mx.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
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

    private final IEstadoRepository estadoRepository;

    private final ICiudadRepository ciudadRepository;

    private final ITorneoRepository torneoRepository;


    @Override
    public List<CategoriaArbitro> findAllCategoriasArbitrosEntity() {
        return categoriaArbitroRepo.findAll();
    }

    @Override
    public List<Nacionalidad> findAllNacionalidadesEntity() {
        return nacionalidadRepo.findAll();
    }

    @Override
    public List<Posicion> findAllPosicionesEntity() {
        return posicionRepo.findAll();
    }

    @Override
    public List<Status> findAllStatusesEntity() {
        return statusRepo.findAll();
    }

    @Override
    public List<TiposAcontecimiento> findAllTiposAcontecimientosEntity() {
        return tiposAcontecimientoRepo.findAll();
    }

    @Override
    public List<Estado> findAllEstado() {
        return estadoRepository.findAll();
    }

    @Override
    public List<Ciudad> findAllCiudad() {
        return ciudadRepository.findAll();
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

    @Override
    public Estado findEstadoById(Short id) {
        return estadoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el estado"));
    }

    @Override
    public Ciudad findCiudadById(Short id) {
        return ciudadRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro la ciudad"));
    }

    @Override
    public CategoriaArbitro findCategoriaArbitroEntityById(Short id) {
        return categoriaArbitroRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro la categoria"));
    }

    @Override
    public Nacionalidad findNacionalidadEntityById(Short id) {
        return nacionalidadRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro la nacionalidad"));
    }

    @Override
    public Posicion findPosicionEntityById(Short id) {
        return posicionRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro la posicion"));
    }

    @Override
    public Status findStatusEntityById(Short id) {
        return statusRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el status"));
    }

    @Override
    public TiposAcontecimiento findTipoAcontecimientoEntityById(Short id) {
        return tiposAcontecimientoRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro tipo Acontecimiento"));
    }

    @Override
    public Torneo findTorneoEntityById(Long id) {
        return torneoRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro el torneo"));
    }
}
