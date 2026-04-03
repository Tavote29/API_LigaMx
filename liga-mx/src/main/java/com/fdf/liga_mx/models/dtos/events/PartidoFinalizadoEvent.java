package com.fdf.liga_mx.models.dtos.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PartidoFinalizadoEvent {

    private final UUID idPartido;
}