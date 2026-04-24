package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.models.dtos.response.*;
import com.fdf.liga_mx.models.entitys.Ciudad;
import com.fdf.liga_mx.models.entitys.Estado;
import com.fdf.liga_mx.services.ICatalogosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogos")
@Tag(name = SwaggerTags.CATALOGOS_TAG, description = SwaggerTags.CATALOGOS_DESC)
public class CatalogosController {

    private final ICatalogosService catalogosService;

    public CatalogosController(ICatalogosService catalogosService) {
        this.catalogosService = catalogosService;
    }

    @GetMapping("/categoriasArbitro")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener todas las categorías de árbitro",
        description = "Retorna una lista de todas las categorías de árbitro disponibles en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de categorías de árbitro obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CategoriaArbitroResponseDto[].class)
        )
    )
    public ResponseEntity<List<CategoriaArbitroResponseDto>> findAllCategoriasArbitros() {
        return ResponseEntity.ok().body(catalogosService.findAllCategoriasArbitros());
    }

    @GetMapping("/nacionalidades")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener todas las nacionalidades",
        description = "Retorna una lista de todas las nacionalidades disponibles en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de nacionalidades obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = NacionalidadResponseDto[].class)
        )
    )
    public ResponseEntity<List<NacionalidadResponseDto>> findAllNacionalidades() {
        return ResponseEntity.ok().body(catalogosService.findAllNacionalidades());
    }

    @GetMapping("/posiciones")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener todas las posiciones",
        description = "Retorna una lista de todas las posiciones de jugador disponibles en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de posiciones obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = PosicionResponseDto[].class)
        )
    )
    public ResponseEntity<List<PosicionResponseDto>> findAllPosiciones() {
        return ResponseEntity.ok().body(catalogosService.findAllPosiciones());
    }

    @GetMapping("/status")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener todos los estados de registro",
        description = "Retorna una lista de todos los estados de registro disponibles en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de estados de registro obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = StatusResponseDto[].class)
        )
    )
    public ResponseEntity<List<StatusResponseDto>> findAllStatuses() {
        return ResponseEntity.ok().body(catalogosService.findAllStatuses());
    }

    @GetMapping("/ciudades")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener todas las ciudades",
        description = "Retorna una lista de todas las ciudades disponibles en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de ciudades obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Ciudad[].class)
        )
    )
    public ResponseEntity<List<Ciudad>> findAllCiudades() {
        return ResponseEntity.ok().body(catalogosService.findAllCiudad());
    }

    @GetMapping("/estados")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener todos los estados",
        description = "Retorna una lista de todos los estados disponibles en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de estados obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Estado[].class)
        )
    )
    public ResponseEntity<List<Estado>> findAllEstados() {
        return ResponseEntity.ok().body(catalogosService.findAllEstado());
    }

    @GetMapping("/tiposAcontecimientos")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener todos los tipos de acontecimientos",
        description = "Retorna una lista de todos los tipos de acontecimientos disponibles en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de tipos de acontecimientos obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = TiposAcontecimientoResponseDto[].class)
        )
    )
    public ResponseEntity<List<TiposAcontecimientoResponseDto>> findAllTiposAcontecimientos() {
        return ResponseEntity.ok().body(catalogosService.findAllTiposAcontecimientos());
    }

    @GetMapping("/categoriasArbitro/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener categoría de árbitro por ID",
        description = "Retorna los detalles de una categoría de árbitro específica basada en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Categoría de árbitro encontrada",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CategoriaArbitroResponseDto.class)
        )
    )
    public ResponseEntity<CategoriaArbitroResponseDto> findCategoriaArbitroById(
            @Parameter(
                description = "ID de la categoría de árbitro",
                required = true,
                example = "1"
            )
            @PathVariable Short id){
        return ResponseEntity.ok().body(catalogosService.findCategoriaArbitroById(id));
    }

    @GetMapping("/nacionalidades/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener nacionalidad por ID",
        description = "Retorna los detalles de una nacionalidad específica basada en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Nacionalidad encontrada",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = NacionalidadResponseDto.class)
        )
    )
    public ResponseEntity<NacionalidadResponseDto> findNacionalidadById(
            @Parameter(
                description = "ID de la nacionalidad",
                required = true,
                example = "1"
            )
            @PathVariable Short id){
        return ResponseEntity.ok().body(catalogosService.findNacionalidadById(id));
    }

    @GetMapping("/posiciones/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener posición por ID",
        description = "Retorna los detalles de una posición específica basada en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Posición encontrada",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = PosicionResponseDto.class)
        )
    )
    public ResponseEntity<PosicionResponseDto> findPosicionById(
            @Parameter(
                description = "ID de la posición",
                required = true,
                example = "1"
            )
            @PathVariable Short id){
        return ResponseEntity.ok().body(catalogosService.findPosicionById(id));
    }

    @GetMapping("/status/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener estado de registro por ID",
        description = "Retorna los detalles de un estado de registro específico basado en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Estado de registro encontrado",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = StatusResponseDto.class)
        )
    )
    public ResponseEntity<StatusResponseDto> findStatusById(
            @Parameter(
                description = "ID del estado de registro",
                required = true,
                example = "1"
            )
            @PathVariable Short id){
        return ResponseEntity.ok().body(catalogosService.findStatusById(id));
    }

    @GetMapping("/tiposAcontecimientos/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'BASICO')")
    @Operation(
        summary = "Obtener tipo de acontecimiento por ID",
        description = "Retorna los detalles de un tipo de acontecimiento específico basado en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Tipo de acontecimiento encontrado",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = TiposAcontecimientoResponseDto.class)
        )
    )
    public ResponseEntity<TiposAcontecimientoResponseDto> findTipoAcontecimientoById(
            @Parameter(
                description = "ID del tipo de acontecimiento",
                required = true,
                example = "1"
            )
            @PathVariable Short id){
        return ResponseEntity.ok().body(catalogosService.findTipoAcontecimientoById(id));
    }
}