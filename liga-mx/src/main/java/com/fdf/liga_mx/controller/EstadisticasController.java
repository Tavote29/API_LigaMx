package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.models.dtos.projection.getMarcadorPartido;
import com.fdf.liga_mx.models.dtos.projection.getTablaCociente;
import com.fdf.liga_mx.models.dtos.projection.getTablaGoleoIndividual;
import com.fdf.liga_mx.models.dtos.projection.getTablaPosiciones;
import com.fdf.liga_mx.services.IEstadisticasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estadisticas")
@AllArgsConstructor
@Tag(name = SwaggerTags.ESTADISTICAS_TAG, description = SwaggerTags.ESTADISTICAS_DESC)
public class EstadisticasController {
    private final IEstadisticasService estadisticasService;

    @GetMapping("/tablaPosiciones/{id}")
    @Operation(
            summary = "Tabla de posiciones del torneo",
            description = "Retorna la tabla de posiciones mediante el id del torneo"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Busqueda exitosa",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = getTablaPosiciones.class)
            )
    )
    public ResponseEntity<List<getTablaPosiciones>> tablaPosiciones(
            @Parameter(
                    description = "Id del torneo",
                    required = true,
                    example = "7"
            )
            @PathVariable Long id
    ){
        return ResponseEntity.ok(estadisticasService.obtenerTablaPosiciones(id));
    }

    @GetMapping("/tablaGoleo/{id}")
    @Operation(
            summary = "Tabla de Goleo",
            description = "Retorna la tabla de goleo mediante el id del torneo"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Busqueda Exitosa",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = getTablaGoleoIndividual.class)
            )
    )
    public ResponseEntity<List<getTablaGoleoIndividual>> tablaGoleo(
            @Parameter(
                    description = "ID del torneo",
                    required = true,
                    example = "7"
            )
            @PathVariable Long id){
        return ResponseEntity.ok(estadisticasService.obtenerTablaGoleo(id));
    }

    @GetMapping("/tablaCociente")
    @Operation(
            summary = "Tabla de Cociente",
            description = "Retorna la tabla de cociente segun los resultados de los ultimos 6 torneos. " +
                    "Esta tabla se calcula mediante la division del total de partidos jugados entre los puntos obtenidos en dicho plazo. " +
                    "Esto es util para el descenso y ascenso de categoria, si es que aplica"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Busqueda exitosa",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = getTablaCociente.class)
            )
    )
    public ResponseEntity<List<getTablaCociente>> tablaCociente(){
        return ResponseEntity.ok(estadisticasService.obtenerTablaCociente());
    }

    @GetMapping("/tablaOfensiva/{id}")
    @Operation(
            summary = "Tabla Mejor Ofensiva",
            description = "Retorna la tabla ordenada por goles a favor de manera descendente de cada equipo del torneo requerido"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Busqueda Exitosa",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation =  getMarcadorPartido.class)
            )
    )
    public ResponseEntity<List<getMarcadorPartido>> tablaOfensiva(
            @Parameter(
                    description = "ID del torneo",
                    required = true,
                    example = "7"
            )
            @PathVariable Long id){
        return ResponseEntity.ok(estadisticasService.obtenerTablaOfensiva(id));
    }

    @GetMapping("/tablaDefensiva/{id}")
    @Operation(
            summary = "Tabla Mejor Defensiva",
            description = "Retorna la tabla ordenada de manera ascendente por goles en contra de cada equipo del torneo requerido"
    )
    @SwaggerResponses.CommonApiResponses
    @ApiResponse(
            responseCode = "200",
            description = "Busqueda Exitosa",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation =  getMarcadorPartido.class)
            )
    )
    public ResponseEntity<List<getMarcadorPartido>> tablaDefensiva(
            @Parameter(
                    description = "ID del torneo",
                    required = true,
                    example = "7"
            )
            @PathVariable Long id){
        return ResponseEntity.ok(estadisticasService.obtenerTablaDefensiva(id));
    }
}
