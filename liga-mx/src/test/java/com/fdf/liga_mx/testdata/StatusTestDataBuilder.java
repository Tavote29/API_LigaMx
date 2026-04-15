package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Status;
import com.fdf.liga_mx.models.enums.Estados;
import com.github.javafaker.Faker;

import java.util.LinkedHashSet;
import java.util.Locale;

public class StatusTestDataBuilder {

    private final Status.StatusBuilder builder;

    public StatusTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));
        
        Estados randomEstado = Estados.values()[faker.number().numberBetween(0, Estados.values().length)];

        this.builder = Status.builder()
                .id(randomEstado.getCodigo())
                .descripcionStatus(randomEstado.name())
                .partidos(new LinkedHashSet<>())
                .personas(new LinkedHashSet<>());
    }

    public static StatusTestDataBuilder aStatus() {
        return new StatusTestDataBuilder();
    }

    public StatusTestDataBuilder withId(Short id) {
        builder.id(id);
        return this;
    }

    public StatusTestDataBuilder withDescripcionStatus(String descripcionStatus) {
        builder.descripcionStatus(descripcionStatus);
        return this;
    }

    public Status build() {
        return builder.build();
    }
}
