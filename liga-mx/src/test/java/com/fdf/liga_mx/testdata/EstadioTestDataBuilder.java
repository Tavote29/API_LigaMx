package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Ciudad;
import com.fdf.liga_mx.models.entitys.Estadio;
import com.fdf.liga_mx.models.entitys.Estado;
import com.github.javafaker.Faker;

import java.util.LinkedHashSet;
import java.util.Locale;

public class EstadioTestDataBuilder {

    private final Estadio.EstadioBuilder builder;

    public EstadioTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        Estado estado = EstadoTestDataBuilder.anEstado().build();
        Ciudad ciudad = CiudadTestDataBuilder.aCiudad().withIdEstado(estado).build();

        this.builder = Estadio.builder()
                .id((short) faker.number().numberBetween(1, 100))
                .nombreEstadio(faker.company().name() + " Stadium")
                .direccion(faker.address().streetAddress())
                .capacidad(faker.number().numberBetween(10000, 100000))
                .idEstado(estado)
                .idCiudad(ciudad)
                .clubs(new LinkedHashSet<>())
                .partidos(new LinkedHashSet<>());
    }

    public static EstadioTestDataBuilder anEstadio() {
        return new EstadioTestDataBuilder();
    }

    public EstadioTestDataBuilder withId(Short id) {
        builder.id(id);
        return this;
    }

    public EstadioTestDataBuilder withNombreEstadio(String nombreEstadio) {
        builder.nombreEstadio(nombreEstadio);
        return this;
    }

    public EstadioTestDataBuilder withDireccion(String direccion) {
        builder.direccion(direccion);
        return this;
    }

    public EstadioTestDataBuilder withCapacidad(Integer capacidad) {
        builder.capacidad(capacidad);
        return this;
    }

    public EstadioTestDataBuilder withIdEstado(Estado idEstado) {
        builder.idEstado(idEstado);
        return this;
    }

    public EstadioTestDataBuilder withIdCiudad(Ciudad idCiudad) {
        builder.idCiudad(idCiudad);
        return this;
    }

    public Estadio build() {
        return builder.build();
    }
}
