package com.fdf.liga_mx.models.dtos.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TorneoRequestDto {


    @NotBlank
    private String nombreTorneo;

    @NotNull
    @FutureOrPresent
    private LocalDate fechaInicio;

    @FutureOrPresent
    private LocalDate fechaFin;

}
