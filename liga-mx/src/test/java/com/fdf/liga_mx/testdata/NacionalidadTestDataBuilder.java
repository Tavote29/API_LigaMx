package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Nacionalidad;
import com.github.javafaker.Faker;

import java.util.LinkedHashSet;
import java.util.Locale;

public class NacionalidadTestDataBuilder {

    private final Nacionalidad.NacionalidadBuilder builder;

    public NacionalidadTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        this.builder = Nacionalidad.builder()
                .id((short) faker.number().numberBetween(1, 200))
                .nombreNacionalidad(faker.nation().nationality())
                .personas(new LinkedHashSet<>());
    }

    public static NacionalidadTestDataBuilder aNacionalidad() {
        return new NacionalidadTestDataBuilder();
    }

    public NacionalidadTestDataBuilder withId(Short id) {
        builder.id(id);
        return this;
    }

    public NacionalidadTestDataBuilder withNombreNacionalidad(String nombreNacionalidad) {
        builder.nombreNacionalidad(nombreNacionalidad);
        return this;
    }

    public Nacionalidad build() {
        return builder.build();
    }
}
