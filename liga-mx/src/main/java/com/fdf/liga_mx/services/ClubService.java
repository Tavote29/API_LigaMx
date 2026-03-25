package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.ClubMapper;
import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.repositories.*;
import com.fdf.liga_mx.models.request.ClubRequest;
import com.fdf.liga_mx.models.response.ClubResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ClubService implements IClubService{
    private ICiudadRepository iCiudadRepository;
    private EstadioRepository estadioRepository;
    private DTRepository dtRepository;
    private ClubRepository clubRepository;
    private ClubMapper clubMapper;

    @Override
    public ClubResponse create(ClubRequest clubRequest) {
        var ciudad = iCiudadRepository.findById(clubRequest.getIdCiudad()).orElseThrow();
        var estadio = estadioRepository.findById(clubRequest.getIdEstadio()).orElseThrow();
        var dt = dtRepository.findById(clubRequest.getIdDT()).orElseThrow();

        var club = Club.builder()
                .nombreClub(clubRequest.getNombre())
                .fechaFundacion(clubRequest.getFechaFundacion())
                .propietario(clubRequest.getPropietario())
                .idCiudad(ciudad)
                .idEstadio(estadio)
                .idDt(dt)
                .build();

        var clubPersist = this.clubRepository.save(club);
        log.info("Club registrado con el ID: {}",clubPersist.getId());

        return clubMapper.entityToResponse(clubPersist);
    }

    @Override
    public ClubResponse read(Short aShort) {
        return null;
    }

    @Override
    public ClubResponse update(ClubRequest clubRequest, Short aShort) {
        return null;
    }

    @Override
    public void delete(Short aShort) {

    }
}
