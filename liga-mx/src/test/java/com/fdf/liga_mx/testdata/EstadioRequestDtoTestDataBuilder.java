package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.github.javafaker.Faker;

import java.util.Locale;

public class EstadioRequestDtoTestDataBuilder {

    private final EstadioRequestDto.EstadioRequestDtoBuilder builder;

    public EstadioRequestDtoTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        this.builder = EstadioRequestDto.builder()
                .nombreEstadio(faker.company().name() + " Stadium")
                .direccion(faker.address().fullAddress())
                .capacidad(faker.number().numberBetween(10000, 80000))
                .idEstado((short) faker.number().numberBetween(1, 32))
                .idCiudad((short) faker.number().numberBetween(1, 100));
    }

    public static EstadioRequestDtoTestDataBuilder anEstadioRequestDto() {
        return new EstadioRequestDtoTestDataBuilder();
    }

    public EstadioRequestDtoTestDataBuilder withId(Short id) {
        builder.id(id);
        return this;
    }

    public EstadioRequestDtoTestDataBuilder withNombreEstadio(String nombreEstadio) {
        builder.nombreEstadio(nombreEstadio);
        return this;
    }

    public EstadioRequestDtoTestDataBuilder withDireccion(String direccion) {
        builder.direccion(direccion);
        return this;
    }

    public EstadioRequestDtoTestDataBuilder withCapacidad(Integer capacidad) {
        builder.capacidad(capacidad);
        return this;
    }

    public EstadioRequestDtoTestDataBuilder withIdEstado(Short idEstado) {
        builder.idEstado(idEstado);
        return this;
    }

    public EstadioRequestDtoTestDataBuilder withIdCiudad(Short idCiudad) {
        builder.idCiudad(idCiudad);
        return this;
    }

    public EstadioRequestDto build() {
        return builder.build();
    }
}
