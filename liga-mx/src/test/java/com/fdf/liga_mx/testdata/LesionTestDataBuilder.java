package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Lesion;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import com.fdf.liga_mx.models.entitys.*;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class LesionTestDataBuilder {

    private final Lesion.LesionBuilder builder;

    public LesionTestDataBuilder(){
        Faker faker = new Faker(new Locale("es-MX"));

        Jugador jugador = JugadorTestDataBuilder.aJugador().build();

        this.builder = Lesion.builder()
                .id(UUID.randomUUID())
                .descripcionLesion(faker.lorem().sentence())
                .fechaLesion(faker.date().past(10, TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime())
                .fechaRecuperacion(faker.date().future(60,TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime())
                .jugador(jugador);
    }

    public static LesionTestDataBuilder aLesion(){
        return new LesionTestDataBuilder();
    }

    public LesionTestDataBuilder withId(UUID uuid){
        builder.id(uuid);
        return this;
    }

    public LesionTestDataBuilder withDescripcion(String descripcion){
        builder.descripcionLesion(descripcion);
        return this;
    }

    public LesionTestDataBuilder withFechaLesion(LocalDateTime fechaLesion){
        builder.fechaLesion(fechaLesion);
        return this;
    }

    public LesionTestDataBuilder withFechaRecuperacion(LocalDateTime fechaRecuperacion){
        builder.fechaRecuperacion(fechaRecuperacion);
        return this;
    }

    public LesionTestDataBuilder withJugador(Jugador jugador){
        builder.jugador(jugador);
        return this;
    }

    public Lesion build(){
        return builder.build();
    }
}
