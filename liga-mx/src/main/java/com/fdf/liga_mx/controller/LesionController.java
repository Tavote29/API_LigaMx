package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.models.dtos.request.LesionRequestDto;
import com.fdf.liga_mx.models.dtos.response.LesionResponseDto;
import com.fdf.liga_mx.services.ILesionService;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/lesion")
@AllArgsConstructor
@Tag(name = SwaggerTags.LESION_TAG, description = SwaggerTags.LESION_DESC)
public class LesionController {
    private final ILesionService lesionService;

    @PostMapping
    @Operation(
            summary = "Registrar lesiones",
            description = "Registrar lesiones que sufrieron los jugadores de la liga"
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
            responseCode = "201",
            description = "Se ha registrado con exito la lesion",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = LesionResponseDto.class)
            )
    )
    public ResponseEntity<LesionResponseDto> registrarLesion(
            @Parameter(
                    description = "Datos de la lesion",
                    required = true,
                    schema = @Schema(implementation = LesionRequestDto.class)
            )
            @RequestBody
            @Valid
            LesionRequestDto request
    ){
        LesionResponseDto lesionResponseDto = lesionService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(lesionResponseDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar lesion existente",
            description = "Modificar la informacion de una lesion existente de un jugador basado en su id"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Se ha modificado la informacion con exito",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = LesionResponseDto.class)
            )
    )
    public ResponseEntity<LesionResponseDto> updateLesion(
            @Parameter(
                    description = "Nuevos datos de la lesion",
                    required = true,
                    schema = @Schema(implementation = LesionRequestDto.class)
        )
        @RequestBody LesionRequestDto request,
            @Parameter(
                    description = "ID de la lesion a actualizar",
                    required = true,
                    example = "4AD7DD07-5B29-F111-BCAE-D85ED388D04B"
            )
            @PathVariable UUID id){
        LesionResponseDto lesionResponseDto = lesionService.update(request,id);
        return ResponseEntity.ok(lesionResponseDto);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener informacion de la lesion mediante su UUID",
            description = "Retorna los detalles de una lesion en especifico de un jugador"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Informacion encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = LesionResponseDto.class)
            )
    )
    public ResponseEntity<LesionResponseDto> findLesionById(
            @Parameter(
                    description = "UUID de la lesion",
                    required = true,
                    example = "4AD7DD07-5B29-F111-BCAE-D85ED388D04B"
            )
            @PathVariable UUID uuid
    ){
        return ResponseEntity.ok(lesionService.findDtoById(uuid));
    }
}
