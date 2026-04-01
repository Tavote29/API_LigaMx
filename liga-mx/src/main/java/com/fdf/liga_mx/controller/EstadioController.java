package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.EstadioResponseDto;
import com.fdf.liga_mx.services.IEstadioService;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.config.SwaggerResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadio")
@Tag(name = SwaggerTags.ESTADIO_TAG, description = SwaggerTags.ESTADIO_DESC)
public class EstadioController {

    private final IEstadioService estadioService;

    public EstadioController(IEstadioService estadioService) {
        this.estadioService = estadioService;
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo estadio",
        description = "Registra un nuevo estadio en el sistema"
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "Estadio creado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = EstadioResponseDto.class)
        )
    )
    public ResponseEntity<EstadioResponseDto> saveEstadio(
            @Parameter(
                description = "Datos del estadio a crear",
                required = true,
                schema = @Schema(implementation = EstadioRequestDto.class)
            )
            @RequestBody @Valid EstadioRequestDto estadioRequest) {
        EstadioResponseDto estadioResponseDto = estadioService.save(estadioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(estadioResponseDto);
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los estadios",
        description = "Retorna una lista de todos los estadios registrados en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de estadios obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = EstadioResponseDto[].class)
        )
    )
    public ResponseEntity<List<EstadioResponseDto>> findAllEstadios() {
        return ResponseEntity.ok(estadioService.findAllDto());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener estadio por ID",
        description = "Retorna los detalles de un estadio específico basado en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Estadio encontrado",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = EstadioResponseDto.class)
        )
    )
    public ResponseEntity<EstadioResponseDto> findEstadioById(
            @Parameter(
                description = "ID del estadio",
                required = true,
                example = "1"
            )
            @PathVariable Short id) {
        return ResponseEntity.ok(estadioService.findDtoById(id));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar estadio existente",
        description = "Actualiza la información de un estadio existente basado en su ID"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Estadio actualizado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = EstadioResponseDto.class)
        )
    )
    public ResponseEntity<EstadioResponseDto> updateEstadio(
            @Parameter(
                description = "Nuevos datos del estadio",
                required = true,
                schema = @Schema(implementation = EstadioRequestDto.class)
            )
            @RequestBody EstadioRequestDto estadioRequest,
            @Parameter(
                description = "ID del estadio a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Short id) {
        EstadioResponseDto estadioResponseDto = estadioService.update(estadioRequest, id);
        return ResponseEntity.ok(estadioResponseDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar un estadio",
        description = "Elimina un estadio del sistema basado en su ID"
    )
    @SwaggerResponses.DeleteApiResponses
    public ResponseEntity<Void> deleteEstadio(
            @Parameter(
                description = "ID del estadio a eliminar",
                required = true,
                example = "1"
            )
            @PathVariable Short id) {
        estadioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(
        summary = "Buscar estadios con filtros",
        description = "Realiza una búsqueda paginada de estadios con múltiples filtros"
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
    public ResponseEntity<Page<EstadioResponseDto>> searchStadium(
            @Parameter(
                description = "Número de página (0-indexed)",
                required = false,
                example = "0"
            )
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(
                description = "Tamaño de la página",
                required = false,
                example = "10"
            )
            @RequestParam(defaultValue = "10") Integer size,
            @Parameter(
                description = "Criterios de ordenamiento en formato 'campo,dirección;'",
                required = false,
                example = "nombre_estadio,asc;"
            )
            @RequestParam(required = false,defaultValue = "nombre_estadio,asc;") String sorts,
            @Parameter(
                description = "ID de ciudad para filtrar",
                required = false,
                example = "1"
            )
            @RequestParam(required = false) Short ciudadId,
            @Parameter(
                description = "ID de estado para filtrar",
                required = false,
                example = "1"
            )
            @RequestParam(required = false) Short estadoId,
            @Parameter(
                description = "Nombre del estadio para filtrar",
                required = false,
                example = "Azteca"
            )
            @RequestParam(required = false) String nombre,
            @Parameter(
                description = "Capacidad mínima para filtrar",
                required = false,
                example = "10000"
            )
            @RequestParam(required = false) Integer minCapacity,
            @Parameter(
                description = "Capacidad máxima para filtrar",
                required = false,
                example = "50000"
            )
            @RequestParam(required = false) Integer maxCapacity) {
        
        Page<EstadioResponseDto> response = estadioService.searchStadium(page, size, sorts, ciudadId, estadoId, nombre, minCapacity, maxCapacity);
        return ResponseEntity.ok(response);
    }
}
