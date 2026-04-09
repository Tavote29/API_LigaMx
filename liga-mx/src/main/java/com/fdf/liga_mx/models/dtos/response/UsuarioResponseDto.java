package com.fdf.liga_mx.models.dtos.response;

import com.fdf.liga_mx.models.enums.Roles;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class UsuarioResponseDto {
    private Long id;
    private String email;
    private String username;
    private Roles role;
}
