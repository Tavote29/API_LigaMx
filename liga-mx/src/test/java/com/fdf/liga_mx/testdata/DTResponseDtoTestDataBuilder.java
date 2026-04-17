package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.models.dtos.response.PersonaResponseDto;
import com.fdf.liga_mx.models.entitys.DT;
import com.github.javafaker.Faker;

import java.util.Locale;

public class DTResponseDtoTestDataBuilder {

    private final DTResponseDto.DTResponseDtoBuilder builder;

    public DTResponseDtoTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        PersonaResponseDto persona = PersonaResponseDtoTestDataBuilder.aPersonaResponseDto().build();

        this.builder = DTResponseDto.builder()
                .id(faker.number().randomNumber())
                .tarjetasAmarillas((short) faker.number().numberBetween(0, 10))
                .tarjetasRojas((short) faker.number().numberBetween(0, 5))
                .idPersona(persona);
    }

    public static DTResponseDtoTestDataBuilder aDTResponseDto() {
        return new DTResponseDtoTestDataBuilder();
    }

    public DTResponseDtoTestDataBuilder withId(Long id) {
        builder.id(id);
        return this;
    }

    public DTResponseDtoTestDataBuilder withTarjetasAmarillas(Short tarjetasAmarillas) {
        builder.tarjetasAmarillas(tarjetasAmarillas);
        return this;
    }

    public DTResponseDtoTestDataBuilder withTarjetasRojas(Short tarjetasRojas) {
        builder.tarjetasRojas(tarjetasRojas);
        return this;
    }

    public DTResponseDtoTestDataBuilder withIdPersona(PersonaResponseDto idPersona) {
        builder.idPersona(idPersona);
        return this;
    }

    public DTResponseDtoTestDataBuilder fromEntity(DT dt) {
        if (dt != null) {
            this.withId(dt.getId())
                .withTarjetasAmarillas(dt.getTarjetasAmarillas())
                .withTarjetasRojas(dt.getTarjetasRojas());

            if (dt.getPersona() != null) {
                PersonaResponseDto personaDto = PersonaResponseDtoTestDataBuilder.aPersonaResponseDto()
                        .fromEntity(dt.getPersona())
                        .build();
                this.withIdPersona(personaDto);
            }
        }
        return this;
    }

    public DTResponseDto build() {
        return builder.build();
    }
}
