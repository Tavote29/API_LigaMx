package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Posicion;
import com.github.javafaker.Faker;

import java.util.LinkedHashSet;
import java.util.Locale;

public class PosicionTestDataBuilder {

    private final Posicion.PosicionBuilder builder;

    public PosicionTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));
        
        String[] posiciones = {"Portero", "Defensa Central", "Lateral Derecho", "Lateral Izquierdo", "Medio Centro", "Medio Ofensivo", "Extremo Derecho", "Extremo Izquierdo", "Delantero Centro"};

        Short id = (short) faker.number().numberBetween(0, 8);

        String posicionAleatoria = posiciones[id];



        this.builder = Posicion.builder()
                .id(id)
                .descripcionPosicion(posicionAleatoria)
                .jugadores(new LinkedHashSet<>());
    }

    public static PosicionTestDataBuilder aPosicion() {
        return new PosicionTestDataBuilder();
    }

    public PosicionTestDataBuilder withId(Short id) {
        builder.id(id);
        return this;
    }

    public PosicionTestDataBuilder withDescripcionPosicion(String descripcionPosicion) {
        builder.descripcionPosicion(descripcionPosicion);
        return this;
    }

    public Posicion build() {
        return builder.build();
    }
}
