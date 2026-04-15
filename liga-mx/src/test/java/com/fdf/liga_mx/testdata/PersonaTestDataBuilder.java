package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Nacionalidad;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.entitys.Status;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.UUID;

public class PersonaTestDataBuilder {

    private final Persona.PersonaBuilder builder;

    public PersonaTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        Status status = StatusTestDataBuilder.aStatus().build();
        Nacionalidad nacionalidad = NacionalidadTestDataBuilder.aNacionalidad().build();

        this.builder = Persona.builder()
                .id(UUID.randomUUID())
                .nombre(faker.name().fullName())
                .fechaNacimiento(faker.date().birthday(18, 40).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .lugarNacimiento(faker.address().city())
                .estatura(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 2)).setScale(2, RoundingMode.HALF_UP))
                .peso(BigDecimal.valueOf(faker.number().randomDouble(1, 60, 100)).setScale(1, RoundingMode.HALF_UP))
                .idStatus(status)
                .idNacionalidad(nacionalidad)
                .imageUrl(faker.internet().url())
                .arbitros(new LinkedHashSet<>())
                .DTS(new LinkedHashSet<>())
                .jugadores(new LinkedHashSet<>());
    }

    public static PersonaTestDataBuilder aPersona() {
        return new PersonaTestDataBuilder();
    }

    public PersonaTestDataBuilder withId(UUID id) {
        builder.id(id);
        return this;
    }

    public PersonaTestDataBuilder withNombre(String nombre) {
        builder.nombre(nombre);
        return this;
    }

    public PersonaTestDataBuilder withFechaNacimiento(LocalDate fechaNacimiento) {
        builder.fechaNacimiento(fechaNacimiento);
        return this;
    }

    public PersonaTestDataBuilder withLugarNacimiento(String lugarNacimiento) {
        builder.lugarNacimiento(lugarNacimiento);
        return this;
    }

    public PersonaTestDataBuilder withEstatura(BigDecimal estatura) {
        builder.estatura(estatura);
        return this;
    }

    public PersonaTestDataBuilder withPeso(BigDecimal peso) {
        builder.peso(peso);
        return this;
    }

    public PersonaTestDataBuilder withIdStatus(Status idStatus) {
        builder.idStatus(idStatus);
        return this;
    }

    public PersonaTestDataBuilder withIdNacionalidad(Nacionalidad idNacionalidad) {
        builder.idNacionalidad(idNacionalidad);
        return this;
    }

    public PersonaTestDataBuilder withImageUrl(String imageUrl) {
        builder.imageUrl(imageUrl);
        return this;
    }

    public Persona build() {
        return builder.build();
    }
}
