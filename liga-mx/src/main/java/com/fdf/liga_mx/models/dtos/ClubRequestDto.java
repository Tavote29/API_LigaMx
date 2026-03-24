package com.fdf.liga_mx.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubRequestDto {
    @NotNull(message = "El id no puede ser nulo")
    private Short id;

    @NotNull(message = "El nombre del club no puede ser nulo")
    private Short nombreClub;

    @NotNull(message = "La fecha de fundación no puede ser nula")
    private LocalDate fechaFundacion;

    @NotBlank(message = "El propietario no puede estar vacío")
    @Size(max = 255)
    private String propietario;

    @NotNull(message = "El estado no puede ser nulo")
    private Short idEstado;

    @NotNull(message = "La ciudad no puede ser nula")
    private Short idCiudad;

    @NotNull(message = "El DT no puede ser nulo")
    private Long idDt;

    @NotNull(message = "El estadio no puede ser nulo")
    private Short idEstadio;
}