package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.models.dtos.request.ClubRequest;
import com.fdf.liga_mx.models.dtos.response.ClubResponseDto;
import com.fdf.liga_mx.services.IClubService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/club")
public class ClubController {

    private final IClubService clubService;


    public  ClubController(IClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping
    public ResponseEntity<ClubResponseDto> saveClub(@RequestBody @Valid ClubRequest clubRequest) {

        ClubResponseDto clubResponseDto = clubService.save(clubRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(clubResponseDto);
    }

}
