package com.fdf.liga_mx.controller;


import com.fdf.liga_mx.models.dtos.request.ArbitroRequest;
import com.fdf.liga_mx.models.dtos.response.ArbitroResponseDto;
import com.fdf.liga_mx.services.IArbitroService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "arbitro")
@AllArgsConstructor
public class ArbitroController {
    private final IArbitroService arbitroService;

    @PostMapping
    public ResponseEntity<ArbitroResponseDto> post(@RequestBody ArbitroRequest arbitroRequest){
        return ResponseEntity.ok(arbitroService.save(arbitroRequest));
    }
}
