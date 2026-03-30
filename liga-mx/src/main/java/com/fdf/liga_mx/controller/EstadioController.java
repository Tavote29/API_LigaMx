package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.EstadioResponseDto;
import com.fdf.liga_mx.services.IEstadioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadio")
public class EstadioController {

    private final IEstadioService estadioService;

    public EstadioController(IEstadioService estadioService) {
        this.estadioService = estadioService;
    }

    @PostMapping
    public ResponseEntity<EstadioResponseDto> saveEstadio(@RequestBody @Valid EstadioRequestDto estadioRequest) {
        EstadioResponseDto estadioResponseDto = estadioService.save(estadioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(estadioResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<EstadioResponseDto>> findAllEstadios() {
        return ResponseEntity.ok(estadioService.findAllDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadioResponseDto> findEstadioById(@PathVariable Short id) {
        return ResponseEntity.ok(estadioService.findDtoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadioResponseDto> updateEstadio(@RequestBody EstadioRequestDto estadioRequest, @PathVariable Short id) {
        EstadioResponseDto estadioResponseDto = estadioService.update(estadioRequest, id);
        return ResponseEntity.ok(estadioResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstadio(@PathVariable Short id) {
        estadioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EstadioResponseDto>> searchStadium(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false,defaultValue = "nombre_estadio,asc;") String sorts,
            @RequestParam(required = false) Short ciudadId,
            @RequestParam(required = false) Short estadoId,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity) {
        
        Page<EstadioResponseDto> response = estadioService.searchStadium(page, size, sorts, ciudadId, estadoId, nombre, minCapacity, maxCapacity);
        return ResponseEntity.ok(response);
    }
}
