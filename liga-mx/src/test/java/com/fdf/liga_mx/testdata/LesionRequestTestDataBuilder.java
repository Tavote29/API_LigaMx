package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.request.LesionRequestDto;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LesionRequestTestDataBuilder {

    private final LesionRequestDto.LesionRequestDtoBuilder builder;

    public LesionRequestTestDataBuilder(){
        Faker faker = new Faker(new Locale("es-MX"));

        this.builder = LesionRequestDto.builder()
                .descripcionLesion(faker.lorem().sentence())
                .fechaLesion(faker.date().past(10, TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime())
                .fechaEstimadaRecuperacion(faker.date().future(60,TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime())
                .nuiJugador(faker.number().randomNumber());
    }

    public static LesionRequestTestDataBuilder aLesionRequest(){
        return new LesionRequestTestDataBuilder();
    }

    public LesionRequestTestDataBuilder withDescripcion(String descripcion){
        builder.descripcionLesion(descripcion);
        return this;
    }
    public LesionRequestTestDataBuilder withFechaLesion(LocalDateTime fechaLesion){
        builder.fechaLesion(fechaLesion);
        return this;
    }
    public LesionRequestTestDataBuilder withFechaRecuperacion(LocalDateTime fechaRecuperacion){
        builder.fechaEstimadaRecuperacion(fechaRecuperacion);
        return this;
    }
    public LesionRequestTestDataBuilder withNuiJugador(Long nuiJugador){
        builder.nuiJugador(nuiJugador);
        return this;
    }

    public LesionRequestDto build(){
        return builder.build();
    }
}
