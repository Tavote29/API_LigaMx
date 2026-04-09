package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.models.dtos.projection.getMarcadorPartido;
import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.request.PartidoRequest;
import com.fdf.liga_mx.models.dtos.response.PartidoResponseDto;
import com.fdf.liga_mx.services.IPartidoService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/partidos")
@AllArgsConstructor
@Tag(name = SwaggerTags.PARTIDO_TAG, description = SwaggerTags.PARTIDO_DESC)
public class PartidoController {
    private final IPartidoService partidoService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
            summary = "Crear un nuevo partido",
            description = "Registra un nuevo partido en el sistema"
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
            responseCode = "201",
            description = "Partido creado con exito",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PartidoResponseDto.class)
            )
    )
    public ResponseEntity<PartidoResponseDto> post(
            @Parameter(description = "Datos del partido a crear",
                        required = true,
                        schema = @Schema(implementation = PartidoRequest.class))
            @RequestBody PartidoRequest request){
        PartidoResponseDto partidoResponseDto = partidoService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(partidoResponseDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
            summary = "Obtener el partido por id",
            description = "Regresa los detalles del partido basado en el id proporcionado"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Partido encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PartidoResponseDto.class)
            )
    )
    public ResponseEntity<PartidoResponseDto> findPartidobyId(
            @Parameter(
                    description = "ID del partido",
                    required = true,
                    example = "4AD7DD07-5B29-F111-BCAE-D85ED388D04B"
            )
            @PathVariable UUID id){
        return  ResponseEntity.ok(partidoService.findDtoById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
            summary = "Obtener todos los partidos",
            description = "Retorna una lista de todos los partidos registrados en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Partidos obtenidos de manera exitosa",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PartidoResponseDto[].class)
    )
    )
    public ResponseEntity<List<PartidoResponseDto>> findAllPartidos(){
        return ResponseEntity.ok(partidoService.findAllDto());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
            summary = "Actualizar partido con el id proporcionado",
            description = "Metodo para actualizar los datos de un partido con el id proporcionado"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "La informacion del partido ha sido actualizada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PartidoResponseDto.class)
            )
    )
    public ResponseEntity<PartidoResponseDto> updatePartido(
            @Parameter(
                    description = "Nuevos datos del partido seleccionado",
                    required = true,
                    schema = @Schema(implementation = PartidoRequest.class)
            )
            @RequestBody PartidoRequest request,
            @Parameter(
                    description = "ID del partido",
                    required = true,
                    example = "4AD7DD07-5B29-F111-BCAE-D85ED388D04B"
            )
            @PathVariable UUID id){
        PartidoResponseDto partidoResponseDto = partidoService.update(request,id);
        return ResponseEntity.ok(partidoResponseDto);
    }

    @GetMapping("/marcador/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
            summary = "Marcador del partido",
            description = "Metodo para obtener el mercado del partido mediante su id"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Busqueda exitosa",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = getMarcadorPartido.class)
            )

    )
    public ResponseEntity<List<getMarcadorPartido>> obtenerMarcadorPartido(
            @Parameter(
                    description = "ID del partido",
                    required = true,
                    example = "4AD7DD07-5B29-F111-BCAE-D85ED388D04B"
            )
            @PathVariable UUID id){
        return ResponseEntity.ok(partidoService.obtenerMarcadorPartido(id));
    }
}
