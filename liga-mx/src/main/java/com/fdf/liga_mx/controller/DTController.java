package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.models.request.DTRequest;
import com.fdf.liga_mx.models.response.DTResponse;
import com.fdf.liga_mx.services.IDTService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "dt")
@AllArgsConstructor
public class DTController {
    private final IDTService idtService;

    @PostMapping
    public ResponseEntity<DTResponse> post(@RequestBody DTRequest dtRequest){
        return ResponseEntity.ok(idtService.create(dtRequest));
    }
}
