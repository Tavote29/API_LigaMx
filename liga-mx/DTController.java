package com.fdf.liga_mx.controller;


import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.services.IDTService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
