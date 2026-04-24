package com.fdf.liga_mx.services;


import com.fdf.liga_mx.mappers.AcontecimientoMapper;
import com.fdf.liga_mx.models.dtos.events.PartidoFinalizadoEvent;
import com.fdf.liga_mx.models.dtos.request.AcontecimientoRequest;
import com.fdf.liga_mx.models.dtos.response.AcontecimientoResponseDto;
import com.fdf.liga_mx.models.dtos.response.ResumenCambiosDto;
import com.fdf.liga_mx.models.entitys.Acontecimiento;
import com.fdf.liga_mx.models.entitys.Partido;
import com.fdf.liga_mx.models.entitys.TiposAcontecimiento;
import com.fdf.liga_mx.models.enums.TiposAcontecimientos;
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


        TiposAcontecimiento tipoAcont = catalogosService.findTipoAcontecimientoEntityById(request.getIdTipo());

        if (acontecimiento.getIdJugadorPrimario()!=null)
            acontecimiento.setIdJugadorPrimario(jugadorService.findById(acontecimiento.getIdJugadorPrimario().getId()));

        if (acontecimiento.getIdJugadorSecundario()!=null)
            acontecimiento.setIdJugadorSecundario(jugadorService.findById(acontecimiento.getIdJugadorSecundario().getId()));

        if (partido.getIdStatus().getDescripcionStatus().equals("FINALIZADO"))
            throw new IllegalStateException("Partido finalizado");



        if (TiposAcontecimientos.CAMBIO.getCodigo().equals(request.getIdTipo())){
            verificacionCambioValido(request,partido);
        }


        acontecimiento.setIdTipo(tipoAcont);
        acontecimiento.setIdPartido(partido);

        Acontecimiento savedAcontecimiento = acontecimientoRepo.saveAndFlush(acontecimiento);

        if ("FIN PARTIDO".equals(tipoAcont.getDescripcionTipo()))
            eventPublisher.publishEvent(new PartidoFinalizadoEvent(savedAcontecimiento.getIdPartido().getId()));



        return acontecimientoMapper.toDto(savedAcontecimiento);

    }


    @Override
    @Transactional(readOnly = true)
    public void verificacionCambioValido(AcontecimientoRequest request, Partido partido) {

        Long idIn = request.getIdJugadorPrimario();
        Long idOut = request.getIdJugadorSecundario();

        if (!jugadorService.isMatchPlayer(partido.getId(),idIn) || !jugadorService.isMatchPlayer(partido.getId(),idOut))
            throw  new IllegalStateException("Jugador no forma parte del partido");


        if (idIn == null || idOut == null) {
            throw new IllegalArgumentException("Los jugadores no pueden ser nulos");
        }
        if (idIn.equals(idOut)) {
            throw new IllegalArgumentException("Los jugadores no pueden ser iguales");
        }

        List<ResumenCambiosDto> resumenCambios = partidoService.obtenerResumenCambios(partido.getId());

        if (resumenCambios.isEmpty()) return;

        boolean jugadorYaParticipo = resumenCambios.stream()
                .flatMap(resumen -> resumen.detalles().stream())
                .anyMatch(cambio ->
                        cambio.jugadorOut().equals(idIn) ||
                                cambio.jugadorIn().equals(idIn)  ||
                                cambio.jugadorOut().equals(idOut)
                );

        if (jugadorYaParticipo) {
            throw new IllegalStateException("Uno de los jugadores ya participo en un cambio previo");
        }

        Short idClubCambio = jugadorService.findById(idOut).getIdClub().getId();

        boolean limiteSuperado = resumenCambios.stream()
                .filter(resumen -> resumen.idClub().equals(idClubCambio))
                .anyMatch(resumen -> resumen.totalCambios() >= 5);

        if (limiteSuperado) {
            throw new IllegalStateException("El equipo ya alcanzó el limite máximo de cambios");
        }

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


}
