package com.fdf.liga_mx.models.dtos.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthRequestDto {


    @NotBlank
    private String username;


    @NotBlank
    private String password;

}