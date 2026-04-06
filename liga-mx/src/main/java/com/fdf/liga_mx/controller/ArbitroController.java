package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.models.dtos.request.ArbitroRequest;
import com.fdf.liga_mx.models.dtos.response.ArbitroResponseDto;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.services.IArbitroService;
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

@RestController
@RequestMapping(path = "/arbitro")
@AllArgsConstructor
@Tag(name = SwaggerTags.ARBITRO_TAG, description = SwaggerTags.ARBITRO_DESC)
public class ArbitroController {
    private final IArbitroService arbitroService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Crear un nuevo árbitro",
        description = "Registra un nuevo árbitro en el sistema"
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "Árbitro creado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ArbitroResponseDto.class)
        )
    )
    public ResponseEntity<ArbitroResponseDto> post(
            @Parameter(
                    description = "Imagen del árbitro",
                    required = false
            )
            @RequestPart(value = "imagen", required = false) MultipartFile file,
            @Parameter(
                description = "Datos del árbitro a crear",
                required = true,
                schema = @Schema(implementation = ArbitroRequest.class)
            )
            @RequestPart("arbitro") @Valid ArbitroRequest arbitroRequest) throws IOException {

        if (file!=null && !Utils.isValidImage(file))
            throw new IllegalArgumentException(("El archivo no es una imagen válida"));

        ArbitroResponseDto arbitroResponseDto = arbitroService.save(arbitroRequest, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(arbitroResponseDto);
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los árbitros",
        description = "Retorna una lista de todos los árbitros registrados en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de árbitros obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ArbitroResponseDto[].class)
        )
    )
    public ResponseEntity<List<ArbitroResponseDto>> findAllArbitros(){
        return ResponseEntity.ok(arbitroService.findAllDto());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener árbitro por ID",
        description = "Retorna los detalles de un árbitro específico basado en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Árbitro encontrado",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ArbitroResponseDto.class)
        )
    )
    public ResponseEntity<ArbitroResponseDto> findArbitroById(
            @Parameter(
                description = "ID del árbitro",
                required = true,
                example = "1"
            )
            @PathVariable Long id){
        return ResponseEntity.ok(arbitroService.findDtoById(id));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar árbitro existente",
        description = "Actualiza la información de un árbitro existente basado en su ID"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Árbitro actualizado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ArbitroResponseDto.class)
        )
    )
    public ResponseEntity<ArbitroResponseDto> updateArbitro(
            @Parameter(
                description = "Nuevos datos del árbitro",
                required = true,
                schema = @Schema(implementation = ArbitroRequest.class)
            )
            @RequestBody ArbitroRequest arbitroRequest,
            @Parameter(
                description = "ID del árbitro a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Long id){
        ArbitroResponseDto arbitroResponseDto = arbitroService.update(arbitroRequest,id);
        return ResponseEntity.ok(arbitroResponseDto);
    }

    @GetMapping("/search")
    @Operation(
        summary = "Buscar árbitros con filtros",
        description = "Realiza una búsqueda paginada de árbitros con múltiples filtros"
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
    public ResponseEntity<Page<ArbitroResponseDto>> searchArbitro(
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
                example = "nombre,asc;"
            )
            @RequestParam(required = false, defaultValue = "nombre,asc;") String sorts,
            @Parameter(
                description = "Nombre para filtrar",
                required = false,
                example = "Jorge"
            )
            @RequestParam(required = false) String nombre,
            @Parameter(
                description = "ID de nacionalidad para filtrar",
                required = false,
                example = "1"
            )
            @RequestParam(required = false) Integer nacionalidad,
            @Parameter(
                description = "ID de categoría para filtrar",
                required = false,
                example = "1"
            )
            @RequestParam(required = false) Short categoria
    ){
        Page<ArbitroResponseDto> response =  arbitroService.searchArbitro(page, size, sorts, nombre, nacionalidad, categoria);
        return ResponseEntity.ok(response);
    }
}
