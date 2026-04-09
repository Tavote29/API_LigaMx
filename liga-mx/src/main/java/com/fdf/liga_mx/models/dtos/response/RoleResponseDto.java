package com.fdf.liga_mx.models.dtos.response;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class RoleResponseDto {
    private Short id;
    private String rolName;
}
