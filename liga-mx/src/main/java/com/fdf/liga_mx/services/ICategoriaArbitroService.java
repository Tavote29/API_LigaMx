package com.fdf.liga_mx.services;


import com.fdf.liga_mx.models.dtos.request.CategoriaArbitroRequestDto;
import com.fdf.liga_mx.models.dtos.response.CategoriaArbitroResponseDto;
import com.fdf.liga_mx.models.entitys.CategoriaArbitro;

public interface ICategoriaArbitroService extends CrudService<CategoriaArbitroRequestDto, CategoriaArbitroResponseDto, CategoriaArbitro, Short> {
}
