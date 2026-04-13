package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.UsuarioRequestDto;
import com.fdf.liga_mx.models.dtos.response.UsuarioResponseDto;
import com.fdf.liga_mx.models.entitys.Usuario;

public interface IUsuarioService{

    Usuario findByUsername(String username);

    UsuarioResponseDto save(UsuarioRequestDto usuarioRequestDto);

    Usuario findById(Long id);

}
