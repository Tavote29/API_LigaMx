package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.UsuarioRequestDto;
import com.fdf.liga_mx.models.dtos.response.UsuarioResponseDto;
import com.fdf.liga_mx.models.entitys.Usuario;

public interface IUsuarioService extends CrudService<UsuarioRequestDto, UsuarioResponseDto, Usuario, Long>{

    Usuario findByUsername(String username);

}
