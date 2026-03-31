package com.fdf.liga_mx.controller;


import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.services.IDTService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/dt")
@AllArgsConstructor
public class DTController {

    private final IDTService idtService;

    @PostMapping
    public ResponseEntity<DTResponseDto> post(@RequestBody DTRequest dtRequest){
        DTResponseDto dtResponseDto = idtService.save(dtRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtResponseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DTResponseDto> findDTbyId(@PathVariable Long id){
        return ResponseEntity.ok(idtService.findDtoById(id));
    }
    @GetMapping
    public ResponseEntity<List<DTResponseDto>> findAllDT(){
        return ResponseEntity.ok(idtService.findAllDto());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTResponseDto> updateDT(@RequestBody DTRequest dtRequest, @PathVariable Long id){
        DTResponseDto dtResponseDto = idtService.update(dtRequest,id);
        return ResponseEntity.ok(dtResponseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DTResponseDto>> searchDT(
            @RequestParam(defaultValue =  "0") Integer page,
            @RequestParam(defaultValue = "10")  Integer size,
            @RequestParam(required = false, defaultValue = "nombre_jugador,asc;") String sorts,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer nacionalidad,
            @RequestParam(required = false) Short club
    ){
        Page<DTResponseDto> response =  idtService.searchDT(page, size, sorts, nombre, nacionalidad, club);
        return ResponseEntity.ok(response);
    }


}
