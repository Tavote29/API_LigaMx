package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.projection.getMarcadorPartido;
import com.fdf.liga_mx.models.dtos.projection.getTablaCociente;
import com.fdf.liga_mx.models.dtos.projection.getTablaGoleoIndividual;
import com.fdf.liga_mx.models.dtos.projection.getTablaPosiciones;

import java.util.List;

public interface IEstadisticasService {
    List<getTablaPosiciones> obtenerTablaPosiciones(Long id);
    List<getTablaGoleoIndividual> obtenerTablaGoleo(Long id);
    List<getTablaCociente> obtenerTablaCociente();
    List<getMarcadorPartido> obtenerTablaOfensiva(Long id);
    List<getMarcadorPartido> obtenerTablaDefensiva(Long id);
}
