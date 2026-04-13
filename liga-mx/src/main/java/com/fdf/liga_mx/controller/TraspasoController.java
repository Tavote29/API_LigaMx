package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.models.dtos.request.TraspasoRequestDTO;
import com.fdf.liga_mx.models.dtos.response.TraspasoResponseDto;
import com.fdf.liga_mx.services.ITraspasoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/draft")
@AllArgsConstructor
@Tag(name = SwaggerTags.TRASPASOS_TAG, description = SwaggerTags.TRASPASOS_DESC)
public class TraspasoController {
    private final ITraspasoService traspasoService;

    @PostMapping
    @Operation(
            summary = "Crear traspaso",
            description = "Registra un nuevo traspaso de jugador"
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
            responseCode = "201",
            description = "Traspado creado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TraspasoResponseDto.class)
            )
    )
    public ResponseEntity<TraspasoResponseDto> post(TraspasoRequestDTO request){
        TraspasoResponseDto traspasoResponseDto = traspasoService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(traspasoResponseDto);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener el traspaso dado su id",
            description = "Retorna los detalles del traspaso segun su id"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Elemento encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TraspasoResponseDto.class)
            )
    )
    public ResponseEntity<TraspasoResponseDto> findTraspasoById(
            @Parameter(
                    description = "UUID del traspaso",
                    required = true,
                    example = "793"
            )
            @PathVariable UUID uuid){
        return ResponseEntity.ok(traspasoService.findDtoById(uuid));
    }

    @GetMapping
    @Operation(
            summary = "Obtiene todos los traspasos",
            description = "Lista de todos los traspasos que se tienen registrados"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida exitosamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TraspasoResponseDto[].class)
            )
    )
    public ResponseEntity<List<TraspasoResponseDto>> findAllTraspasos(){
        return ResponseEntity.ok(traspasoService.findAllDto());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualiza el traspaso dado su uuid",
            description = "Actualiza la informacion del traspaso basado en su uuid"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "El traspaso ha sido actualizado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TraspasoResponseDto.class)
            )
    )
    public ResponseEntity<TraspasoResponseDto> updateTraspaso(
            @Parameter(
                    description = "Nuevos datos del traspaso",
                    required = true,
                    schema = @Schema(implementation = TraspasoRequestDTO.class)
            )
            @RequestBody TraspasoRequestDTO request,
            @Parameter(
                    description = "Uuid del traspaso a actualizar",
                    required = true,
                    example = "793"
            )
            @PathVariable UUID uuid){
        TraspasoResponseDto traspasoResponseDto = traspasoService.update(request,uuid);
        return ResponseEntity.ok(traspasoResponseDto);
    }
}
