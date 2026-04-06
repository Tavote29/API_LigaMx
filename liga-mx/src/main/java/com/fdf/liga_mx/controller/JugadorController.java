package com.fdf.liga_mx.controller;


import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.services.IJugadorService;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jugador")
@AllArgsConstructor
@Tag(name = SwaggerTags.JUGADOR_TAG, description = SwaggerTags.JUGADOR_DESC)
public class JugadorController {
    private final IJugadorService jugadorService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Crear un nuevo jugador",
        description = "Registra un nuevo jugador en el sistema"
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "Jugador creado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = JugadorResponseDto.class)
        )
    )
    public ResponseEntity<JugadorResponseDto> post(
            @Parameter(
                    description = "Imagen del jugador",
                    required = false
            )
            @RequestPart(value = "imagen", required = false) MultipartFile file,
            @Parameter(
                description = "Datos del jugador a crear",
                required = true,
                schema = @Schema(implementation = JugadorRequest.class)
            )
            @RequestPart("jugador") @Valid JugadorRequest jugadorRequest) throws IOException {

        if (file!=null && !Utils.isValidImage(file))
            throw new IllegalArgumentException(("El archivo no es una imagen válida"));

        JugadorResponseDto jugadorResponsetDto =  jugadorService.save(jugadorRequest,file);
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorResponsetDto);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener jugador por ID",
        description = "Retorna los detalles de un jugador específico basado en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Jugador encontrado",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = JugadorResponseDto.class)
        )
    )
    public ResponseEntity<JugadorResponseDto> findJugadorById(
            @Parameter(
                description = "ID del jugador",
                required = true,
                example = "1"
            )
            @PathVariable Long id){
        return ResponseEntity.ok(jugadorService.findDtoById(id));
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los jugadores",
        description = "Retorna una lista de todos los jugadores registrados en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de jugadores obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = JugadorResponseDto[].class)
        )
    )
    public ResponseEntity<List<JugadorResponseDto>> findAllJugadores(){
        return ResponseEntity.ok(jugadorService.findAllDto());
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar jugador existente",
        description = "Actualiza la información de un jugador existente basado en su ID"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Jugador actualizado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = JugadorResponseDto.class)
        )
    )
    public ResponseEntity<JugadorResponseDto> updateJugador(
            @Parameter(
                description = "Nuevos datos del jugador",
                required = true,
                schema = @Schema(implementation = JugadorRequest.class)
            )
            @RequestBody JugadorRequest jugadorRequest,
            @Parameter(
                description = "ID del jugador a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Long id){
        JugadorResponseDto jugadorResponseDto = jugadorService.update(jugadorRequest, id);
        return ResponseEntity.ok(jugadorResponseDto);
    }

    @GetMapping("/search")
    @Operation(
        summary = "Buscar jugadores con filtros",
        description = "Realiza una búsqueda paginada de jugadores con múltiples filtros"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Resultados de búsqueda obtenidos exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Page.class)
        )
    )
    public ResponseEntity<Page<JugadorResponseDto>> searchJugador(
            @Parameter(
                description = "Número de página (0-indexed)",
                required = false,
                example = "0"
            )
            @RequestParam(defaultValue =  "0") Integer page,
            @Parameter(
                description = "Tamaño de la página",
                required = false,
                example = "10"
            )
            @RequestParam(defaultValue = "10")  Integer size,
            @Parameter(
                description = "Criterios de ordenamiento en formato 'campo,dirección;'",
                required = false,
                example = "nombre_jugador,asc;"
            )
            @RequestParam(required = false, defaultValue = "nombre_jugador,asc;") String sorts,
            @Parameter(
                description = "Nombre para filtrar",
                required = false,
                example = "Javier"
            )
            @RequestParam(required = false) String nombre,
            @Parameter(
                description = "ID de nacionalidad para filtrar",
                required = false,
                example = "1"
            )
            @RequestParam(required = false) Integer nacionalidad,
            @Parameter(
                description = "ID del club para filtrar",
                required = false,
                example = "1"
            )
            @RequestParam(required = false) Short clubId
            ) {
        Page<JugadorResponseDto> response = jugadorService.searchJugador(page, size, sorts, nombre, nacionalidad, clubId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{jugadorId}/tarjetas/torneo/{torneoId}")
    @Operation(
        summary = "Obtener tarjetas y faltas de un jugador por torneo",
        description = "Retorna la cantidad de tarjetas amarillas, rojas y faltas cometidas por un jugador en un torneo específico"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Estadísticas del jugador obtenidas exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Map.class)
        )
    )
    public ResponseEntity<Map<String, Integer>> obtenerTarjetasJugadorPorTorneoId(
            @Parameter(
                description = "ID del jugador",
                required = true,
                example = "1"
            )
            @PathVariable Long jugadorId,
            @Parameter(
                description = "ID del torneo",
                required = true,
                example = "1"
            )
            @PathVariable Long torneoId
            ) {
        return ResponseEntity.ok(jugadorService.obtenerTarjetasJugadorPorTorneoId(jugadorId, torneoId));
    }
}
