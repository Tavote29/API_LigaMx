package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.models.dtos.request.ClubRequest;
import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.ClubResponseDto;
import com.fdf.liga_mx.services.IClubService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {

    private final IClubService clubService;

    public ClubController(IClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping
    public ResponseEntity<ClubResponseDto> saveClub(@RequestBody @Valid ClubRequest clubRequest) {
        ClubResponseDto clubResponseDto = clubService.save(clubRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(clubResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ClubResponseDto>> findAllClubes() {
        return ResponseEntity.ok(clubService.findAllDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubResponseDto> findClubById(@PathVariable Short id) {
        return ResponseEntity.ok(clubService.findDtoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubResponseDto> updateClub(@PathVariable Short id, @RequestBody ClubRequest clubRequest) {
        return ResponseEntity.ok(clubService.update(clubRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Short id) {
        clubService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idClub}/change-stadium")
    public ResponseEntity<ClubResponseDto> changeStadium(@PathVariable Short idClub, @RequestBody  EstadioRequestDto estadioRequestDto) {
        return ResponseEntity.ok(clubService.changeStadium(estadioRequestDto, idClub));
    }

    @PatchMapping("/{idClub}/assign-dt")
    public ResponseEntity<Void> assignDT(@PathVariable Short idClub, @RequestBody DTRequest dtRequest) {
        clubService.assignDT(dtRequest, idClub);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estado/{id}")
    public ResponseEntity<List<ClubResponseDto>> findClubesByEstadoId(@PathVariable Short id) {
        return ResponseEntity.ok(clubService.findByEstadoId(id));
    }

    @GetMapping("/ciudad/{id}")
    public ResponseEntity<List<ClubResponseDto>> findClubesByCiudadId(@PathVariable Short id) {
        return ResponseEntity.ok(clubService.findByCiudadId(id));
    }
}
