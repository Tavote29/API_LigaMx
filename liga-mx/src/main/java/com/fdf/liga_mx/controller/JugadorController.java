package com.fdf.liga_mx.controller;


import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.services.IJugadorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
