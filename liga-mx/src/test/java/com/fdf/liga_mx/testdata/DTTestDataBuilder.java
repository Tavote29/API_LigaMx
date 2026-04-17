package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.entitys.DT;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.enums.Estados;
import com.github.javafaker.Faker;

import java.util.Locale;

public class DTTestDataBuilder {

    private final DT.DTBuilder builder;

    public DTTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        Persona persona = PersonaTestDataBuilder.aPersona().build();

        this.builder = DT.builder()
                .id(faker.number().randomNumber())
                .tarjetasAmarillas((short) faker.number().numberBetween(0, 10))
                .tarjetasRojas((short) faker.number().numberBetween(0, 5))
                .persona(persona)
                .club(ClubTestDataBuilder.aClub().build())
                .status(Estados.ACTIVO.getCodigo());
    }

    public static DTTestDataBuilder aDT() {
        return new DTTestDataBuilder();
    }

    public DTTestDataBuilder withId(Long id) {
        builder.id(id);
        return this;
    }

    public DTTestDataBuilder withTarjetasAmarillas(Short tarjetasAmarillas) {
        builder.tarjetasAmarillas(tarjetasAmarillas);
        return this;
    }

    public DTTestDataBuilder withTarjetasRojas(Short tarjetasRojas) {
        builder.tarjetasRojas(tarjetasRojas);
        return this;
    }

    public DTTestDataBuilder withPersona(Persona persona) {
        builder.persona(persona);
        return this;
    }

    public DTTestDataBuilder withClub(Club club) {
        builder.club(club);
        return this;
    }

    public DTTestDataBuilder withStatus(Short status) {
        builder.status(status);
        return this;
    }

    public DT build() {
        return builder.build();
    }

    public DTTestDataBuilder fromRequest(DTRequest request) {
        if (request != null) {
            if (request.getNUI_DT() != null) {
                this.withId(request.getNUI_DT());
            }

            if (request.getPersona() != null) {
                Persona persona = PersonaTestDataBuilder.aPersona()
                        .fromRequest(request.getPersona())
                        .build();
                this.withPersona(persona);
            }

            if (request.getIdClub() != null) {
                Club club = ClubTestDataBuilder.aClub()
                        .withId(request.getIdClub())
                        .build();
                this.withClub(club);
            }
        }
        return this;
    }
}
