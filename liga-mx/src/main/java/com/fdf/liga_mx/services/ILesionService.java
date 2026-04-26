package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.LesionRequestDto;
import com.fdf.liga_mx.models.dtos.response.LesionResponseDto;
import com.fdf.liga_mx.models.entitys.Lesion;

import java.util.UUID;

public interface ILesionService extends CrudService<LesionRequestDto, LesionResponseDto, Lesion, UUID>{
}
