package com.fdf.liga_mx.controller;


import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.services.IJugadorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "jugador")
@AllArgsConstructor
public class JugadorController {
    private final IJugadorService jugadorService;

    @PostMapping
    public ResponseEntity<JugadorResponseDto> post(@RequestBody JugadorRequest jugadorRequest){
        return ResponseEntity.ok(jugadorService.save(jugadorRequest));
    }
}
