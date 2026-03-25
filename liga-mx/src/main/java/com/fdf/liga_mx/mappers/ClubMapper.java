package com.fdf.liga_mx.mappers;

import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.response.ClubResponse;
import org.springframework.stereotype.Component;

@Component
public class ClubMapper {
    public ClubResponse entityToResponse(Club entity){
        if(entity == null) return null;
        ClubResponse response = new ClubResponse();
        response.setIdClub(entity.getId());
        response.setIdCiudad(entity.getIdCiudad().getId());
        response.setNombre(entity.getNombreClub());
        response.setPropietario(entity.getPropietario());
        response.setIdEstado(entity.getIdCiudad().getIdEstado().getId());
        response.setFechaFundacion(entity.getFechaFundacion());
        return response;
    }
}
