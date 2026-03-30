package com.fdf.liga_mx.models.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClubRequest implements Serializable {
    @NotNull(message = "El nombre del club no puede ser nulo")
    private String nombreClub;

    @NotNull(message = "La fecha de fundación no puede ser nula")
    private LocalDate fechaFundacion;

    @NotBlank(message = "El propietario no puede estar vacío")
    @Size(max = 255)
    private String propietario;

    @NotNull(message = "El estado no puede ser nulo")
    private Short idEstado;

    @NotNull(message = "La ciudad no puede ser nula")
    private Short idCiudad;


    private Long idDt;


    private Short idEstadio;
}
