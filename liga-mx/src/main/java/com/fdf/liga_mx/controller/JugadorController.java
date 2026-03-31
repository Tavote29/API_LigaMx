package com.fdf.liga_mx.controller;


import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.services.IJugadorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jugador")
@AllArgsConstructor
public class JugadorController {
    private final IJugadorService jugadorService;

    @PostMapping
    public ResponseEntity<JugadorResponseDto> post(@RequestBody JugadorRequest jugadorRequest){
        JugadorResponseDto jugadorResponsetDto =  jugadorService.save(jugadorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorResponsetDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<JugadorResponseDto> findJugadorById(@PathVariable Long id){
        return ResponseEntity.ok(jugadorService.findDtoById(id));
    }
    @GetMapping
    public ResponseEntity<List<JugadorResponseDto>> findAllJugadores(){
        return ResponseEntity.ok(jugadorService.findAllDto());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JugadorResponseDto> updateJugador(@RequestBody JugadorRequest jugadorRequest, @PathVariable Long id){
        JugadorResponseDto jugadorResponseDto = jugadorService.update(jugadorRequest, id);
        return ResponseEntity.ok(jugadorResponseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<JugadorResponseDto>> searchJugador(
            @RequestParam(defaultValue =  "0") Integer page,
            @RequestParam(defaultValue = "10")  Integer size,
            @RequestParam(required = false, defaultValue = "nombre_jugador,asc;") String sorts,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer nacionalidad,
            @RequestParam(required = false) Short clubId
            ) {
        Page<JugadorResponseDto> response = jugadorService.searchJugador(page, size, sorts, nombre, nacionalidad, clubId);

        return ResponseEntity.ok(response);
    }


}
