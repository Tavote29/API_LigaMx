package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.events.PartidoFinalizadoEvent;
import com.fdf.liga_mx.models.dtos.projection.getTarjetasPorPartido;
import com.fdf.liga_mx.models.dtos.request.PartidoRequest;
import com.fdf.liga_mx.models.dtos.response.PartidoResponseDto;
import com.fdf.liga_mx.models.entitys.Partido;
import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.repository.PartidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PartidoServiceImpl implements IPartidoService {

    private final PartidoRepository partidoRepo;

    private final ICatalogosService catalogosService;

    private final IJugadorService jugadorService;

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

        return partidoRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el partido"));
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

    @Override
    @Transactional
    @EventListener
    public void finalizarPartido(PartidoFinalizadoEvent event) {
        Partido partido = partidoRepo.findById(event.getIdPartido()).orElseThrow(() -> new NoSuchElementException("No se encontro el partido"));

        partido.setIdStatus(catalogosService.findStatusEntityById(Estados.FINALIZADO.getCodigo()));

        jugadorService.updateTarjetasByPartidoId(partido.getId());

        partidoRepo.save(partido);
    }
}
