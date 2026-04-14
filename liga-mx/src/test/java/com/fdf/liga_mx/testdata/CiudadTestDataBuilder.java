package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Ciudad;
import com.fdf.liga_mx.models.entitys.Estado;
import com.github.javafaker.Faker;

import java.util.LinkedHashSet;
import java.util.Locale;

public class CiudadTestDataBuilder {

    private final Ciudad.CiudadBuilder builder;

    public CiudadTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));
        
        Estado estado = EstadoTestDataBuilder.anEstado().build();

        this.builder = Ciudad.builder()
                .id((short) faker.number().numberBetween(1, 100))
                .nombreCiudad(faker.address().city())
                .idEstado(estado)
                .clubs(new LinkedHashSet<>())
                .estadios(new LinkedHashSet<>());
    }

    public static CiudadTestDataBuilder aCiudad() {
        return new CiudadTestDataBuilder();
    }

    public CiudadTestDataBuilder withId(Short id) {
        builder.id(id);
        return this;
    }

    public CiudadTestDataBuilder withNombreCiudad(String nombreCiudad) {
        builder.nombreCiudad(nombreCiudad);
        return this;
    }

    public CiudadTestDataBuilder withIdEstado(Estado idEstado) {
        builder.idEstado(idEstado);
        return this;
    }

    public Ciudad build() {
        return builder.build();
    }
}
