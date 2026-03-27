package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.models.dtos.response.*;
import com.fdf.liga_mx.services.ICatalogosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/catalogos")
public class CatalogosController {


    private final ICatalogosService catalogosService;

    public CatalogosController(ICatalogosService catalogosService) {
        this.catalogosService = catalogosService;
    }

    @GetMapping("/categoriasArbitro")
    public ResponseEntity<List<CategoriaArbitroResponseDto>> findAllCategoriasArbitros() {
        return ResponseEntity.ok().body(catalogosService.findAllCategoriasArbitros());
    }

    @GetMapping("/nacionalidades")
    public ResponseEntity<List<NacionalidadResponseDto>> findAllNacionalidades() {
        return ResponseEntity.ok().body(catalogosService.findAllNacionalidades());
    }

    @GetMapping("/posiciones")
    public ResponseEntity<List<PosicionResponseDto>> findAllPosiciones() {
        return ResponseEntity.ok().body(catalogosService.findAllPosiciones());
    }

    @GetMapping("/status")
    public ResponseEntity<List<StatusResponseDto>> findAllStatuses() {
        return ResponseEntity.ok().body(catalogosService.findAllStatuses());
    }

    @GetMapping("/tiposAcontecimientos")
    public ResponseEntity<List<TiposAcontecimientoResponseDto>> findAllTiposAcontecimientos() {
        return ResponseEntity.ok().body(catalogosService.findAllTiposAcontecimientos());
    }

    @GetMapping("/categoriasArbitro/{id}")
    public ResponseEntity<CategoriaArbitroResponseDto> findCategoriaArbitroById(@PathVariable Short id){
        return ResponseEntity.ok().body(catalogosService.findCategoriaArbitroById(id));
    }

    @GetMapping("/nacionalidades/{id}")
    public ResponseEntity<NacionalidadResponseDto> findNacionalidadById(@PathVariable Short id){
        return ResponseEntity.ok().body(catalogosService.findNacionalidadById(id));
    }

    @GetMapping("/posiciones/{id}")
    public ResponseEntity<PosicionResponseDto> findPosicionById(@PathVariable Short id){
        return ResponseEntity.ok().body(catalogosService.findPosicionById(id));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<StatusResponseDto> findStatusById(@PathVariable Short id){
        return ResponseEntity.ok().body(catalogosService.findStatusById(id));
    }

    @GetMapping("/tiposAcontecimientos/{id}")
    public ResponseEntity<TiposAcontecimientoResponseDto> findTipoAcontecimientoById(@PathVariable Short id){
        return ResponseEntity.ok().body(catalogosService.findTipoAcontecimientoById(id));
    }




}
