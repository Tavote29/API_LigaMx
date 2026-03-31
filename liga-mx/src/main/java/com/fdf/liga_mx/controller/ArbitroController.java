package com.fdf.liga_mx.controller;


import com.fdf.liga_mx.models.dtos.request.ArbitroRequest;
import com.fdf.liga_mx.models.dtos.response.ArbitroResponseDto;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.services.IArbitroService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/arbitro")
@AllArgsConstructor
public class ArbitroController {
    private final IArbitroService arbitroService;

    @PostMapping
    public ResponseEntity<ArbitroResponseDto> post(@RequestBody ArbitroRequest arbitroRequest){
        return ResponseEntity.ok(arbitroService.save(arbitroRequest));
    }

    @GetMapping
    public ResponseEntity<List<ArbitroResponseDto>> findAllArbitros(){
        return ResponseEntity.ok(arbitroService.findAllDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArbitroResponseDto> findArbitroById(@PathVariable Long id){
        return ResponseEntity.ok(arbitroService.findDtoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArbitroResponseDto> updateArbitro(@RequestBody ArbitroRequest arbitroRequest,@PathVariable Long id){
        ArbitroResponseDto arbitroResponseDto = arbitroService.update(arbitroRequest,id);
        return ResponseEntity.ok(arbitroResponseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ArbitroResponseDto>> searchDT(
            @RequestParam(defaultValue =  "0") Integer page,
            @RequestParam(defaultValue = "10")  Integer size,
            @RequestParam(required = false, defaultValue = "nombre_jugador,asc;") String sorts,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer nacionalidad,
            @RequestParam(required = false) Short categoria
    ){
        Page<ArbitroResponseDto> response =  arbitroService.searchArbitro(page, size, sorts, nombre, nacionalidad, categoria);
        return ResponseEntity.ok(response);
    }
}
