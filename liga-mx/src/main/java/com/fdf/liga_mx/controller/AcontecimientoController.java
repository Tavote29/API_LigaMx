package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.models.dtos.request.AcontecimientoRequest;
import com.fdf.liga_mx.models.dtos.response.AcontecimientoResponseDto;
import com.fdf.liga_mx.services.IAcontecimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acontecimiento")
@AllArgsConstructor
@Tag(name = SwaggerTags.ACONTECIMIENTO_TAG, description = SwaggerTags.ACONTECIMIENTO_DESC)
public class AcontecimientoController {

    private final IAcontecimientoService acontecimientoService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
            summary = "Crear un nuevo acontecimiento",
            description = "Registra un nuevo acontecimiento en el sistema, como un gol, una tarjeta o el fin de un partido."
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
            responseCode = "201",
            description = "Acontecimiento creado exitosamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AcontecimientoResponseDto.class)
            )
    )
    public ResponseEntity<AcontecimientoResponseDto> registrarAcontecimiento(
            @Parameter(
                    description = "Datos del acontecimiento a crear",
                    required = true,
                    schema = @Schema(implementation = AcontecimientoRequest.class)
            )
            @RequestBody @Valid AcontecimientoRequest acontecimientoRequest) {
        AcontecimientoResponseDto acontecimientoResponseDto = acontecimientoService.save(acontecimientoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(acontecimientoResponseDto);
    }
}
