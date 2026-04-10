package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.models.dtos.request.ClubRequest;
import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.ClubResponseDto;
import com.fdf.liga_mx.services.IClubService;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/club")
@Tag(name = SwaggerTags.CLUB_TAG, description = SwaggerTags.CLUB_DESC)
public class ClubController {

    private final IClubService clubService;

    public ClubController(IClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
        summary = "Crear un nuevo club",
        description = "Crea un nuevo club de fútbol con la información proporcionada"
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "Club creado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ClubResponseDto.class)
        )
    )
    public ResponseEntity<ClubResponseDto> saveClub(
            @Parameter(
                description = "Datos del club a crear",
                required = true,
                schema = @Schema(implementation = ClubRequest.class)
            )
            @RequestBody @Valid ClubRequest clubRequest) {
        ClubResponseDto clubResponseDto = clubService.save(clubRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(clubResponseDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener todos los clubes",
        description = "Retorna una lista de todos los clubes registrados en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de clubes obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ClubResponseDto[].class)
        )
    )
    public ResponseEntity<List<ClubResponseDto>> findAllClubes() {
        return ResponseEntity.ok(clubService.findAllDto());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener club por ID",
        description = "Retorna los detalles de un club específico basado en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Club encontrado",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ClubResponseDto.class)
        )
    )
    public ResponseEntity<ClubResponseDto> findClubById(
            @Parameter(
                description = "ID del club a buscar",
                required = true,
                example = "1"
            )
            @PathVariable Short id) {
        return ResponseEntity.ok(clubService.findDtoById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
        summary = "Actualizar un club existente",
        description = "Actualiza la información de un club existente basado en su ID"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Club actualizado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ClubResponseDto.class)
        )
    )
    public ResponseEntity<ClubResponseDto> updateClub(
            @Parameter(
                description = "ID del club a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Short id,
            @Parameter(
                description = "Nuevos datos del club",
                required = true,
                schema = @Schema(implementation = ClubRequest.class)
            )
            @RequestBody ClubRequest clubRequest) {
        return ResponseEntity.ok(clubService.update(clubRequest, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
        summary = "Eliminar un club",
        description = "Elimina un club del sistema basado en su ID"
    )
    @SwaggerResponses.DeleteApiResponses
    public ResponseEntity<Void> deleteClub(
            @Parameter(
                description = "ID del club a eliminar",
                required = true,
                example = "1"
            )
            @PathVariable Short id) {
        clubService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idClub}/change-stadium")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
        summary = "Cambiar estadio de un club",
        description = "Asigna un nuevo estadio a un club específico"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Estadio cambiado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ClubResponseDto.class)
        )
    )
    public ResponseEntity<ClubResponseDto> changeStadium(
            @Parameter(
                description = "ID del club",
                required = true,
                example = "1"
            )
            @PathVariable Short idClub,
            @Parameter(
                description = "Datos del nuevo estadio",
                required = true,
                schema = @Schema(implementation = EstadioRequestDto.class)
            )
            @RequestBody EstadioRequestDto estadioRequestDto) {
        return ResponseEntity.ok(clubService.changeStadium(estadioRequestDto, idClub));
    }

    @PatchMapping("/{idClub}/assign-dt")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
        summary = "Asignar director técnico a un club",
        description = "Asigna un director técnico a un club específico"
    )
    @SwaggerResponses.UpdateApiResponses
    public ResponseEntity<Void> assignDT(
            @Parameter(
                description = "ID del club",
                required = true,
                example = "1"
            )
            @PathVariable Short idClub,
            @Parameter(
                description = "Datos del director técnico",
                required = true,
                schema = @Schema(implementation = DTRequest.class)
            )
            @RequestBody DTRequest dtRequest) {
        clubService.assignDT(dtRequest, idClub);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estado/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener clubes por estado",
        description = "Retorna una lista de clubes que pertenecen a un estado específico"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de clubes obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ClubResponseDto[].class)
        )
    )
    public ResponseEntity<List<ClubResponseDto>> findClubesByEstadoId(
            @Parameter(
                description = "ID del estado",
                required = true,
                example = "1"
            )
            @PathVariable Short id) {
        return ResponseEntity.ok(clubService.findByEstadoId(id));
    }

    @GetMapping("/ciudad/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener clubes por ciudad",
        description = "Retorna una lista de clubes que pertenecen a una ciudad específica"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de clubes obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ClubResponseDto[].class)
        )
    )
    public ResponseEntity<List<ClubResponseDto>> findClubesByCiudadId(
            @Parameter(
                description = "ID de la ciudad",
                required = true,
                example = "1"
            )
            @PathVariable Short id) {
        return ResponseEntity.ok(clubService.findByCiudadId(id));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping(value = "/escudo/{idClub}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Actualizar escudo de un club",
        description = "Actualiza la imagen del escudo de un club mediante un archivo de imagen (requiere formato multipart/form-data)"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Escudo actualizado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ClubResponseDto.class)
        )
    )
    public ResponseEntity<ClubResponseDto> updateEscudo(
            @Parameter(
                description = "Archivo de imagen para el nuevo escudo",
                required = true
            )
            @RequestPart(value = "imagen", required = true) MultipartFile file,
            @Parameter(
                description = "ID del club al que se le actualizará el escudo",
                required = true,
                example = "1"
            )
            @PathVariable Short idClub) throws IOException {

        if (file!=null && !Utils.isValidImage(file))
            throw new IllegalArgumentException(("El archivo no es una imagen válida"));

        return ResponseEntity.ok(clubService.updateEscudo(file, idClub));
    }

}
