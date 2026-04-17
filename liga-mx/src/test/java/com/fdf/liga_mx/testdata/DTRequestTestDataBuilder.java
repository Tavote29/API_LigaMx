package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.request.PersonaRequest;
import com.github.javafaker.Faker;

import java.util.Locale;

public class DTRequestTestDataBuilder {

    private final DTRequest.DTRequestBuilder builder;

    public DTRequestTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        PersonaRequest personaRequest = PersonaRequestTestDataBuilder.aPersonaRequest().build();

        this.builder = DTRequest.builder()
                .NUI_DT(faker.number().randomNumber())
                .persona(personaRequest)
                .idClub((short) faker.number().numberBetween(1, 18));
    }

    public static DTRequestTestDataBuilder aDTRequest() {
        return new DTRequestTestDataBuilder();
    }

    public DTRequestTestDataBuilder withNUIDT(Long NUI_DT) {
        builder.NUI_DT(NUI_DT);
        return this;
    }

    public DTRequestTestDataBuilder withPersona(PersonaRequest persona) {
        builder.persona(persona);
        return this;
    }

    public DTRequestTestDataBuilder withIdClub(Short idClub) {
        builder.idClub(idClub);
        return this;
    }

    public DTRequest build() {
        return builder.build();
    }
}
