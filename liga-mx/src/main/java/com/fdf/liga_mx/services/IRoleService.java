package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.RoleRequestDto;
import com.fdf.liga_mx.models.dtos.response.RoleResponseDto;
import com.fdf.liga_mx.models.entitys.Role;

public interface IRoleService extends CrudService<RoleRequestDto, RoleResponseDto, Role, Short>{
}
