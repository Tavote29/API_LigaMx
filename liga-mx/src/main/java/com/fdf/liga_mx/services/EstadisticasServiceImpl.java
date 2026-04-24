package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.projection.getMarcadorPartido;
import com.fdf.liga_mx.models.dtos.projection.getTablaCociente;
import com.fdf.liga_mx.models.dtos.projection.getTablaGoleoIndividual;
import com.fdf.liga_mx.models.dtos.projection.getTablaPosiciones;
import com.fdf.liga_mx.repository.EstadisticasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EstadisticasServiceImpl implements IEstadisticasService {

    private final EstadisticasRepository estadisticasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<getTablaPosiciones> obtenerTablaPosiciones(Long id) {
        List<getTablaPosiciones> tablaPosiciones = estadisticasRepository.obtenerTablaPosiciones(id);
        if (tablaPosiciones == null) throw new RuntimeException("error.estadisticas.server_error");
        return tablaPosiciones;
    }

    @Override
    @Transactional(readOnly = true)
    public List<getTablaGoleoIndividual> obtenerTablaGoleo(Long id) {
        List<getTablaGoleoIndividual> tablaGoleo = estadisticasRepository.obtenerTablaGoleo(id);
        if (tablaGoleo == null) throw new RuntimeException("error.estadisticas.server_error");
        return tablaGoleo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<getTablaCociente> obtenerTablaCociente() {
        List<getTablaCociente> tablaCociente = estadisticasRepository.obtenerTablaCociente();
        if(tablaCociente == null) throw new RuntimeException(("error.estadisticas.server_error"));
        return tablaCociente;
    }

    @Override
    @Transactional(readOnly = true)
    public List<getMarcadorPartido> obtenerTablaOfensiva(Long id) {
        List<getMarcadorPartido> tablaOfensiva = estadisticasRepository.obtenerTablaOfensiva(id);
        if (tablaOfensiva == null) throw new RuntimeException("error.estadisticas.server_error");
        return  tablaOfensiva;
    }

    @Override
    @Transactional(readOnly = true)
    public List<getMarcadorPartido> obtenerTablaDefensiva(Long id) {
        List<getMarcadorPartido> tablaDefensiva = estadisticasRepository.obtenerTablaDefensiva(id);
        if (tablaDefensiva == null) throw new RuntimeException("error.estadisticas.server_error");
        return tablaDefensiva;
    }

}
