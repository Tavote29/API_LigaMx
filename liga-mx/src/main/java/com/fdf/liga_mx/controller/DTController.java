package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.services.IDTService;
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

@RestController
@RequestMapping(path = "/dt")
@AllArgsConstructor
@Tag(name = SwaggerTags.DT_TAG, description = SwaggerTags.DT_DESC)
public class DTController {

    private final IDTService idtService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Crear un nuevo director técnico",
        description = "Registra un nuevo director técnico en el sistema"
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "Director técnico creado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = DTResponseDto.class)
        )
    )
    public ResponseEntity<DTResponseDto> post(
            @Parameter(
                    description = "Imagen del director técnico",
                    required = false
            )
            @RequestPart(value = "imagen", required = false) MultipartFile file,
            @Parameter(
                description = "Datos del director técnico a crear",
                required = true,
                schema = @Schema(implementation = DTRequest.class)
            )
            @RequestPart("dt") @Valid DTRequest dtRequest) throws IOException {

        if (file!=null && !Utils.isValidImage(file))
            throw new IllegalArgumentException(("El archivo no es una imagen válida"));

        DTResponseDto dtResponseDto = idtService.save(dtRequest, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtResponseDto);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener director técnico por ID",
        description = "Retorna los detalles de un director técnico específico basado en su ID"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Director técnico encontrado",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = DTResponseDto.class)
        )
    )
    public ResponseEntity<DTResponseDto> findDTbyId(
            @Parameter(
                description = "ID del director técnico",
                required = true,
                example = "1"
            )
            @PathVariable Long id){
        return ResponseEntity.ok(idtService.findDtoById(id));
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los directores técnicos",
        description = "Retorna una lista de todos los directores técnicos registrados en el sistema"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Lista de directores técnicos obtenida exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = DTResponseDto[].class)
        )
    )
    public ResponseEntity<List<DTResponseDto>> findAllDT(){
        return ResponseEntity.ok(idtService.findAllDto());
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar director técnico existente",
        description = "Actualiza la información de un director técnico existente basado en su ID"
    )
    @SwaggerResponses.UpdateApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Director técnico actualizado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = DTResponseDto.class)
        )
    )
    public ResponseEntity<DTResponseDto> updateDT(
            @Parameter(
                description = "Nuevos datos del director técnico",
                required = true,
                schema = @Schema(implementation = DTRequest.class)
            )
            @RequestBody DTRequest dtRequest,
            @Parameter(
                description = "ID del director técnico a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Long id){
        DTResponseDto dtResponseDto = idtService.update(dtRequest,id);
        return ResponseEntity.ok(dtResponseDto);
    }

    @GetMapping("/search")
    @Operation(
        summary = "Buscar directores técnicos con filtros",
        description = "Realiza una búsqueda paginada de directores técnicos con múltiples filtros"
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
    public ResponseEntity<Page<DTResponseDto>> searchDT(
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
                example = "Miguel"
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
            @RequestParam(required = false) Short club
    ){
        Page<DTResponseDto> response =  idtService.searchDT(page, size, sorts, nombre, nacionalidad, club);
        return ResponseEntity.ok(response);
    }
}
