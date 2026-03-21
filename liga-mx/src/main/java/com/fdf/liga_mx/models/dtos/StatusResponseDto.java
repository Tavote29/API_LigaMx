package com.fdf.liga_mx.models.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StatusResponseDto {
    Short id;
    String descripcionStatus;
}
