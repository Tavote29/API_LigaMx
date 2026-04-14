package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Estado;
import com.github.javafaker.Faker;

import java.util.LinkedHashSet;
import java.util.Locale;

public class EstadoTestDataBuilder {

    private final Estado.EstadoBuilder builder;

    public EstadoTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));
        
        this.builder = Estado.builder()
                .id((short) faker.number().numberBetween(1, 32))
                .nombreEstado(faker.address().state())
                .ciudades(new LinkedHashSet<>())
                .clubs(new LinkedHashSet<>())
                .estadios(new LinkedHashSet<>());
    }

    public static EstadoTestDataBuilder anEstado() {
        return new EstadoTestDataBuilder();
    }

    public EstadoTestDataBuilder withId(Short id) {
        builder.id(id);
        return this;
    }

    public EstadoTestDataBuilder withNombreEstado(String nombreEstado) {
        builder.nombreEstado(nombreEstado);
        return this;
    }

    public Estado build() {
        return builder.build();
    }
}
