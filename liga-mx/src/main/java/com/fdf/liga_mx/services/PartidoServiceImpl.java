package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.PartidoMapper;
import com.fdf.liga_mx.models.dtos.events.PartidoFinalizadoEvent;
import com.fdf.liga_mx.models.dtos.projection.getMarcadorPartido;
import com.fdf.liga_mx.models.dtos.request.PartidoRequest;
import com.fdf.liga_mx.models.dtos.response.PartidoResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.repository.ArbitroRepository;
import com.fdf.liga_mx.repository.ClubRepository;
import com.fdf.liga_mx.repository.EstadioRepository;
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
    private final PartidoMapper partidoMapper;
    private final ICatalogosService catalogosService;

    private final IJugadorService jugadorService;
    private final ClubRepository clubRepository;
    private final ArbitroRepository arbitroRepository;
    private final EstadioRepository estadioRepository;

    @Override
    @Transactional
    public PartidoResponseDto save(PartidoRequest request) {
        Club local = clubRepository.findById(request.getIdLocal()).orElseThrow();
        Club visitante = clubRepository.findById(request.getIdVisitante()).orElseThrow();
        Estadio estadio = estadioRepository.findById(request.getIdEstadio()).orElseThrow();
        Arbitro arbitroCentral = arbitroRepository.findById(request.getIdArbitroCentral()).orElseThrow();
        Arbitro arbitroAsistente1 = arbitroRepository.findById(request.getIdArbitroAsistente1()).orElseThrow();
        Arbitro arbitroAsistente2 = arbitroRepository.findById(request.getIdArbitroAsistente2()).orElseThrow();
        Arbitro cuartoArbitro = arbitroRepository.findById(request.getIdCuartoArbitro()).orElseThrow();
        Status status = catalogosService.findStatusEntityById(request.getIdStatus());
        Torneo torneo = catalogosService.findTorneoEntityById(request.getIdTorneo());

        Partido partido = partidoMapper.toEntity(request);
        partido.setIdLocal(local);
        partido.setIdVisitante(visitante);
        partido.setIdEstadio(estadio);
        partido.setIdArbitroCentral(arbitroCentral);
        partido.setIdArbitroAsistente1(arbitroAsistente1);
        partido.setIdArbitroAsistente2(arbitroAsistente2);
        partido.setIdCuartoArbitro(cuartoArbitro);
        partido.setIdStatus(status);
        partido.setIdTorneo(torneo);

        return partidoMapper.toDto(partidoRepo.saveAndFlush(partido));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partido> findAll() {
        return partidoRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartidoResponseDto> findAllDto() {
        return partidoRepo.findAll().stream().map(partido -> partidoMapper.toDto(partido)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Partido findById(UUID id) {
        return partidoRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el partido"));
    }

    @Override
    public PartidoResponseDto findDtoById(UUID id) {
        Partido partido = partidoRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontro el partido"));
        return partidoMapper.toDto(partido);
    }

    @Override
    public PartidoResponseDto update(PartidoRequest request, UUID id) {
        Partido partido = partidoRepo.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontro el partido"));
        if (!partido.getIdEstadio().getId().equals(request.getIdEstadio())){
            Estadio estadio = estadioRepository.findById(request.getIdEstadio()).orElseThrow();
            partido.setIdEstadio(estadio);
        }

        if (!partido.getFecha().equals(request.getFecha())) partido.setFecha(request.getFecha());

        if (!partido.getIdArbitroCentral().getId().equals(request.getIdArbitroCentral())){
            Arbitro arbitroCentral = arbitroRepository.findById(request.getIdArbitroCentral()).orElseThrow();
            partido.setIdArbitroCentral(arbitroCentral);
        }
        if (!partido.getIdArbitroAsistente1().getId().equals(request.getIdArbitroAsistente1())){
            Arbitro arbitroAsistente1 = arbitroRepository.findById(request.getIdArbitroAsistente1()).orElseThrow();
            partido.setIdArbitroCentral(arbitroAsistente1);
        }
        if (!partido.getIdArbitroAsistente2().getId().equals(request.getIdArbitroAsistente2())){
            Arbitro arbitroAsistente2 = arbitroRepository.findById(request.getIdArbitroAsistente2()).orElseThrow();
            partido.setIdArbitroCentral(arbitroAsistente2);
        }
        if (!partido.getIdCuartoArbitro().getId().equals(request.getIdCuartoArbitro())){
            Arbitro cuartoArbitro = arbitroRepository.findById(request.getIdCuartoArbitro()).orElseThrow();
            partido.setIdArbitroCentral(cuartoArbitro);
        }

        if(!partido.getIdStatus().getId().equals(request.getIdStatus())){
            Status status = catalogosService.findStatusEntityById(request.getIdStatus());
            partido.setIdStatus(status);
        }
        return partidoMapper.toDto(partidoRepo.saveAndFlush(partido));
    }

    @Override
    public void delete(UUID id) {
        //no usar, se debe implementar un metodo de baja
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

    @Override
    @Transactional(readOnly = true)
    public List<getMarcadorPartido> obtenerMarcadorPartido(UUID uuid) {
        List<getMarcadorPartido> marcadorPartido = partidoRepo.obtenerMarcador(uuid);
        if (marcadorPartido == null) throw new RuntimeException("Partido no encontrado");
        return marcadorPartido;
    }

}
