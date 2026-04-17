package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.request.PersonaRequest;
import com.fdf.liga_mx.models.enums.Estados;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

public class PersonaRequestTestDataBuilder {

    private final PersonaRequest.PersonaRequestBuilder builder;

    public PersonaRequestTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));
        this.builder = PersonaRequest.builder()
                .nombre(faker.name().fullName())
                .fechaNacimiento(faker.date().birthday(18, 40).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .lugarNacimiento(faker.address().city())
                .estatura(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 2)).setScale(2, RoundingMode.HALF_UP))
                .peso(BigDecimal.valueOf(faker.number().randomDouble(2, 60, 100)).setScale(2, RoundingMode.HALF_UP))
                .idStatus(Estados.ACTIVO.getCodigo())
                .idNacionalidad((short) faker.number().numberBetween(1, 200));
    }

    public static PersonaRequestTestDataBuilder aPersonaRequest() {
        return new PersonaRequestTestDataBuilder();
    }

    public PersonaRequestTestDataBuilder withNombre(String nombre) {
        builder.nombre(nombre);
        return this;
    }

    public PersonaRequestTestDataBuilder withFechaNacimiento(LocalDate fechaNacimiento) {
        builder.fechaNacimiento(fechaNacimiento);
        return this;
    }

    public PersonaRequestTestDataBuilder withLugarNacimiento(String lugarNacimiento) {
        builder.lugarNacimiento(lugarNacimiento);
        return this;
    }

    public PersonaRequestTestDataBuilder withEstatura(BigDecimal estatura) {
        builder.estatura(estatura);
        return this;
    }

    public PersonaRequestTestDataBuilder withPeso(BigDecimal peso) {
        builder.peso(peso);
        return this;
    }

    public PersonaRequestTestDataBuilder withIdStatus(Short idStatus) {
        builder.idStatus(idStatus);
        return this;
    }

    public PersonaRequestTestDataBuilder withIdNacionalidad(Short idNacionalidad) {
        builder.idNacionalidad(idNacionalidad);
        return this;
    }

    public PersonaRequest build() {
        return builder.build();
    }
}
