package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.StatusRequestDto;
import com.fdf.liga_mx.models.dtos.response.StatusResponseDto;
import com.fdf.liga_mx.models.entitys.Status;

public interface IStatusService extends CrudService<StatusRequestDto, StatusResponseDto, Status, Short> {
}
