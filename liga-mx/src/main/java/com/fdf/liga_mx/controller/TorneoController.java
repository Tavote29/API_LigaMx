package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.models.dtos.request.TorneoRequestDto;
import com.fdf.liga_mx.models.dtos.response.TorneoResponseDto;
import com.fdf.liga_mx.services.ITorneoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/torneo")
@RequiredArgsConstructor
@Tag(name = "Torneo", description = "Endpoints para la gestión de Torneos")
public class TorneoController {

    private final ITorneoService torneoService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
            summary = "Crear un nuevo torneo",
            description = "Crea un nuevo torneo con la información proporcionada (solo accesible por SUPER_ADMIN)"
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
            responseCode = "201",
            description = "Torneo creado exitosamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TorneoResponseDto.class)
            )
    )
    public ResponseEntity<TorneoResponseDto> saveTorneo(
            @Parameter(
                    description = "Datos del torneo a crear",
                    required = true,
                    schema = @Schema(implementation = TorneoRequestDto.class)
            )
            @RequestBody @Valid TorneoRequestDto torneoRequestDto) {
        TorneoResponseDto responseDto = torneoService.save(torneoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/actual")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
            summary = "Obtener el torneo actual",
            description = "Retorna los detalles del torneo que se encuentra activo en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Torneo actual encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TorneoResponseDto.class)
            )
    )
    public ResponseEntity<TorneoResponseDto> findActualTorneo() {
        return ResponseEntity.ok(torneoService.findActualTorneo());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
            summary = "Obtener torneo por ID",
            description = "Retorna los detalles de un torneo específico basado en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Torneo encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TorneoResponseDto.class)
            )
    )
    public ResponseEntity<TorneoResponseDto> findTorneoById(
            @Parameter(
                    description = "ID del torneo a buscar",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id) {
        return ResponseEntity.ok(torneoService.findDtoById(id));
    }

    @PatchMapping("/{id}/status/{statusId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
            summary = "Actualizar estado de un torneo",
            description = "Modifica el estado de un torneo existente basado en su ID (solo accesible por SUPER_ADMIN)"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Estado del torneo actualizado exitosamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TorneoResponseDto.class)
            )
    )
    public ResponseEntity<TorneoResponseDto> updateTorneoStatus(
            @Parameter(
                    description = "ID del torneo",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,
            @Parameter(
                    description = "Código del nuevo estado",
                    required = true,
                    example = "1"
            )
            @PathVariable Short statusId) {
        return ResponseEntity.ok(torneoService.updateStatus(id, statusId));
    }
}
