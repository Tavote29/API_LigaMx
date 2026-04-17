package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.response.NacionalidadResponseDto;
import com.fdf.liga_mx.models.dtos.response.PersonaResponseDto;
import com.fdf.liga_mx.models.dtos.response.StatusResponseDto;
import com.fdf.liga_mx.models.entitys.Persona;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.Locale;
import java.util.UUID;

public class PersonaResponseDtoTestDataBuilder {

    private final PersonaResponseDto.PersonaResponseDtoBuilder builder;

    public PersonaResponseDtoTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        StatusResponseDto status = StatusResponseDto.builder()
                .id((short) faker.number().numberBetween(1, 5))
                .descripcionStatus(faker.lorem().word())
                .build();

        NacionalidadResponseDto nacionalidad = NacionalidadResponseDto.builder()
                .id((short) faker.number().numberBetween(1, 200))
                .nombreNacionalidad(faker.nation().nationality())
                .build();

        this.builder = PersonaResponseDto.builder()
                .id(UUID.randomUUID())
                .nombre(faker.name().fullName())
                .fechaNacimiento(faker.date().birthday(18, 40).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .lugarNacimiento(faker.address().city())
                .imageUrl(faker.internet().url())
                .estatura(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 2)).setScale(2, RoundingMode.HALF_UP))
                .peso(BigDecimal.valueOf(faker.number().randomDouble(1, 60, 100)).setScale(1, RoundingMode.HALF_UP))
                .idStatus(status)
                .idNacionalidad(nacionalidad);
    }

    public static PersonaResponseDtoTestDataBuilder aPersonaResponseDto() {
        return new PersonaResponseDtoTestDataBuilder();
    }

    public PersonaResponseDtoTestDataBuilder withId(UUID id) {
        builder.id(id);
        return this;
    }

    public PersonaResponseDtoTestDataBuilder withNombre(String nombre) {
        builder.nombre(nombre);
        return this;
    }

    public PersonaResponseDtoTestDataBuilder fromEntity(Persona persona) {
        if (persona != null) {
            this.withId(persona.getId())
                .withNombre(persona.getNombre());

            builder.fechaNacimiento(persona.getFechaNacimiento());
            builder.lugarNacimiento(persona.getLugarNacimiento());
            builder.imageUrl(persona.getImageUrl());
            builder.estatura(persona.getEstatura());
            builder.peso(persona.getPeso());

            if (persona.getIdStatus() != null) {
                StatusResponseDto statusDto = StatusResponseDto.builder()
                        .id(persona.getIdStatus().getId())
                        .descripcionStatus(persona.getIdStatus().getDescripcionStatus())
                        .build();
                builder.idStatus(statusDto);
            }

            if (persona.getIdNacionalidad() != null) {
                NacionalidadResponseDto nacionalidadDto = NacionalidadResponseDto.builder()
                        .id(persona.getIdNacionalidad().getId())
                        .nombreNacionalidad(persona.getIdNacionalidad().getNombreNacionalidad())
                        .build();
                builder.idNacionalidad(nacionalidadDto);
            }
        }
        return this;
    }

    public PersonaResponseDto build() {
        return builder.build();
    }
}
