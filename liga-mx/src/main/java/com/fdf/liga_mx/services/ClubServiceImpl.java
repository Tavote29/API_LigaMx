package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.ClubMapper;
import com.fdf.liga_mx.models.dtos.request.ClubRequest;
import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.ClubResponseDto;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.models.dtos.response.EstadioResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.repository.IClubRepository;
import com.fdf.liga_mx.repository.IEstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fdf.liga_mx.models.enums.Estados;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements IClubService{

    private final IClubRepository clubRepo;

    private final ClubMapper clubMapper;

    private final IEstadoRepository estadoRepo;

    private final ICatalogosService catalogosService;

    private final IDTService dtService;

    private final IEstadioService estadioService;

    private final MediaStorageService storageService;


    @Override
    @Transactional
    public ClubResponseDto changeStadium(EstadioRequestDto estadioRequestDto,Short idClub) {

        Club club = clubRepo.findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo()).orElseThrow(() -> new NoSuchElementException("Club no encontrado"));

        if (estadioRequestDto.getId() != null) {

            Estadio estadio = estadioService.findById(estadioRequestDto.getId());

            club.setIdEstadio(estadio);

            return clubMapper.toDto(clubRepo.saveAndFlush(club));
        }

        EstadioResponseDto estadioSaved = estadioService.save(estadioRequestDto);

        Estadio estadio = estadioService.findById(estadioSaved.getId());

        club.setIdEstadio(estadio);

        return clubMapper.toDto(clubRepo.save(club));
    }

    @Override
    @Transactional
    public ClubResponseDto updateEscudo(MultipartFile file, Short idClub) throws IOException {

        Club club = clubRepo.findById(idClub).orElseThrow(() -> new NoSuchElementException("Club no encontrado"));



        if (StringUtils.hasText(club.getImageUrl())){

           club.setImageUrl(storageService.replaceFile(club.getImageUrl(), UUID.randomUUID().toString(),file));

        }else club.setImageUrl(storageService.uploadFile(file,UUID.randomUUID().toString()));



        return clubMapper.toDto(clubRepo.save(club));
    }

    @Override
    @Transactional
    public void assignDT(DTRequest dtRequest,Short idClub) {

        Club club = clubRepo.findById(idClub).orElseThrow(() -> new NoSuchElementException("Club no encontrado"));

        if (dtRequest.getNUI_DT() != null) {

            DT dt = dtService.findById(dtRequest.getNUI_DT());

            if (Estados.fromCode(dt.getStatus()) != Estados.ACTIVO){
                throw new IllegalStateException("El status del DT no se encuentra en activo");
            }

            if(dt.getClub()!=null) throw new IllegalArgumentException("El DT ya tiene un club asignado");

            club.setIdDt(dt);

            clubRepo.save(club);

            return ;

        }

        dtRequest.setIdClub(club.getId());

        DTResponseDto dtSaved = dtService.save(dtRequest);

        DT dt = dtService.findById(dtSaved.getId());

        club.setIdDt(dt);

        clubRepo.save(club);

        return ;
    }

    @Override
    public List<ClubResponseDto> findByEstadoId(Short id) {



        return clubRepo.findByIdEstadoId(id).stream().map(c -> clubMapper.toDto(c)).toList();
    }

    @Override
    public List<ClubResponseDto> findByCiudadId(Short id) {
        return clubRepo.findByIdCiudadId(id).stream().map(c -> clubMapper.toDto(c)).toList();
    }

    @Override
    @Transactional
    public ClubResponseDto save(ClubRequest clubRequest) {

        Estado estado = catalogosService.findEstadoById(clubRequest.getIdEstado());

        Ciudad ciudad = catalogosService.findCiudadById(clubRequest.getIdCiudad());

        Club club = clubMapper.toEntity(clubRequest);

        if (clubRequest.getIdEstadio() != null){
            DT dt = dtService.findById(clubRequest.getIdDt());

            if(dt.getClub()!=null) throw new IllegalArgumentException("El DT ya tiene un club asignado");

            club.setIdDt(dt);
        }

        if (clubRequest.getIdDt() != null){
            Estadio estadio = estadioService.findById(clubRequest.getIdEstadio());
            club.setIdEstadio(estadio);
        }

        club.setIdCiudad(ciudad);

        club.setIdEstado(estado);


        return clubMapper.toDto(clubRepo.saveAndFlush(club));
    }

    @Override
    public List<Club> findAll() {
        return clubRepo.findAll();
    }

    @Override
    public List<ClubResponseDto> findAllDto() {
        return clubRepo.findAll().stream().map(c -> clubMapper.toDto(c)).toList();
    }

    @Override
    public Club findById(Short id) {
        return clubRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Club no encontrado"));
    }

    @Override
    public ClubResponseDto findDtoById(Short id) {
        return clubMapper.toDto(clubRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Club no encontrado")));
    }

    @Override
    @Transactional
    public ClubResponseDto update(ClubRequest request, Short id) {

        Club club = clubRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Club no encontrado"));

        club = clubMapper.updateEntity(request,club);


        return clubMapper.toDto(clubRepo.saveAndFlush(club));
    }

    @Override
    public void delete(Short id) {

        //NO UTILIZAR, IMPLEMENTACION PENDIENTE

    }
}
