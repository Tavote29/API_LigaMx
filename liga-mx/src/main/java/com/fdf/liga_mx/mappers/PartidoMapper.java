package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.dtos.PartidoRequestDto;
import com.fdf.liga_mx.models.dtos.PartidoResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PartidoMapper {

    private final ClubMapper clubMapper;
    private final EstadioMapper estadioMapper;
    private final ArbitroMapper arbitroMapper;
    private final StatusMapper statusMapper;

    public Partido toEntity(PartidoRequestDto request) {
        if (request == null) {
            return null;
        }
        Club local = request.getIdLocal() != null ? Club.builder().id(request.getIdLocal()).build() : null;
        Club visitante = request.getIdVisitante() != null ? Club.builder().id(request.getIdVisitante()).build() : null;
        Estadio estadio = request.getIdEstadio() != null ? Estadio.builder().id(request.getIdEstadio()).build() : null;
        Arbitro central = request.getIdArbitroCentral() != null ? Arbitro.builder().id(request.getIdArbitroCentral()).build() : null;
        Arbitro cuarto = request.getIdCuartoArbitro() != null ? Arbitro.builder().id(request.getIdCuartoArbitro()).build() : null;
        Arbitro asis1 = request.getIdArbitroAsistente1() != null ? Arbitro.builder().id(request.getIdArbitroAsistente1()).build() : null;
        Arbitro asis2 = request.getIdArbitroAsistente2() != null ? Arbitro.builder().id(request.getIdArbitroAsistente2()).build() : null;
        Status status = request.getIdStatus() != null ? Status.builder().id(request.getIdStatus()).build() : null;

        return Partido.builder()
                .id(request.getId())
                .idLocal(local)
                .idVisitante(visitante)
                .idEstadio(estadio)
                .idArbitroCentral(central)
                .idCuartoArbitro(cuarto)
                .idArbitroAsistente1(asis1)
                .idArbitroAsistente2(asis2)
                .fecha(request.getFecha())
                .idStatus(status)
                .build();
    }

    public PartidoResponseDto toDto(Partido entity) {
        if (entity == null) {
            return null;
        }
        return PartidoResponseDto.builder()
                .id(entity.getId())
                .idLocal(clubMapper.toDto(entity.getIdLocal()))
                .idVisitante(clubMapper.toDto(entity.getIdVisitante()))
                .idEstadio(estadioMapper.toDto(entity.getIdEstadio()))
                .idArbitroCentral(arbitroMapper.toDto(entity.getIdArbitroCentral()))
                .idCuartoArbitro(arbitroMapper.toDto(entity.getIdCuartoArbitro()))
                .idArbitroAsistente1(arbitroMapper.toDto(entity.getIdArbitroAsistente1()))
                .idArbitroAsistente2(arbitroMapper.toDto(entity.getIdArbitroAsistente2()))
                .fecha(entity.getFecha())
                .idStatus(statusMapper.toDto(entity.getIdStatus()))
                .build();
    }
}