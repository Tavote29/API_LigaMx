package com.fdf.liga_mx.services;


import com.fdf.liga_mx.mappers.AcontecimientoMapper;
import com.fdf.liga_mx.models.dtos.events.PartidoFinalizadoEvent;
import com.fdf.liga_mx.models.dtos.request.AcontecimientoRequest;
import com.fdf.liga_mx.models.dtos.response.AcontecimientoResponseDto;
import com.fdf.liga_mx.models.dtos.response.TiposAcontecimientoResponseDto;
import com.fdf.liga_mx.models.entitys.Acontecimiento;
import com.fdf.liga_mx.models.entitys.Partido;
import com.fdf.liga_mx.repository.AcontecimientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AcontecimientoServiceImpl implements IAcontecimientoService {

    private final AcontecimientoRepository acontecimientoRepo;

    private final AcontecimientoMapper acontecimientoMapper;

    private final IJugadorService jugadorService;

    private final IPartidoService partidoService;

    private final ICatalogosService catalogosService;

    private final ApplicationEventPublisher eventPublisher;


    @Override
    @Transactional
    public AcontecimientoResponseDto save(AcontecimientoRequest request) {

        Acontecimiento acontecimiento = acontecimientoMapper.toEntity(request);

        Partido partido = partidoService.findById(request.getIdPartido());

        TiposAcontecimientoResponseDto tipoAcontDto = catalogosService.findTipoAcontecimientoById(request.getIdTipo());

        if (acontecimiento.getIdJugadorPrimario()!=null)
            acontecimiento.setIdJugadorPrimario(jugadorService.findById(acontecimiento.getIdJugadorPrimario().getId()));

        if (acontecimiento.getIdJugadorSecundario()!=null)
            acontecimiento.setIdJugadorSecundario(jugadorService.findById(acontecimiento.getIdJugadorSecundario().getId()));




        Acontecimiento savedAcontecimiento = acontecimientoRepo.saveAndFlush(acontecimiento);

        if (savedAcontecimiento.getIdTipo().getDescripcionTipo().equals("FIN_PARTIDO"))
            eventPublisher.publishEvent(new PartidoFinalizadoEvent(savedAcontecimiento.getIdPartido().getId()));



        return acontecimientoMapper.toDto(savedAcontecimiento);

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
    public AcontecimientoResponseDto update(AcontecimientoRequest request, UUID id) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
