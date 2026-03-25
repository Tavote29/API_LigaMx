package com.fdf.liga_mx.models.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AcontecimientoRequest implements Serializable {
    @NotNull(message = "El tipo de acontecimiento no puede ser nulo")
    private Short idTipo;

    @NotBlank(message = "El minuto no puede estar vacío")
    @Size(max = 10)
    private String minuto;

    @NotNull(message = "El jugador primario no puede ser nulo")
    private Long idJugadorPrimario;

    private Long idJugadorSecundario;

    @NotNull(message = "El partido no puede ser nulo")
    private UUID idPartido;
}
