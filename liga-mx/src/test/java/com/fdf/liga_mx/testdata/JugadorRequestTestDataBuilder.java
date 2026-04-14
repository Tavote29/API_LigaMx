package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.request.PersonaRequest;
import com.github.javafaker.Faker;

import java.util.Locale;

public class JugadorRequestTestDataBuilder {

    private final JugadorRequest.JugadorRequestBuilder builder;

    public JugadorRequestTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));
        this.builder = JugadorRequest.builder()
                .persona(PersonaRequestTestDataBuilder.aPersonaRequest().build())
                .dorsal((short) faker.number().numberBetween(1, 99))
                .id_posicion((short) faker.number().numberBetween(1, 9))
                .id_club((short) faker.number().numberBetween(1, 18))
                .tarjetasAmarillas(null)
                .tarjetasRojas(null);
    }

    public static JugadorRequestTestDataBuilder aJugadorRequest() {
        return new JugadorRequestTestDataBuilder();
    }

    public JugadorRequestTestDataBuilder withPersona(PersonaRequest persona) {
        builder.persona(persona);
        return this;
    }

    public JugadorRequestTestDataBuilder withDorsal(Short dorsal) {
        builder.dorsal(dorsal);
        return this;
    }

    public JugadorRequestTestDataBuilder withIdPosicion(Short idPosicion) {
        builder.id_posicion(idPosicion);
        return this;
    }

    public JugadorRequestTestDataBuilder withIdClub(Short idClub) {
        builder.id_club(idClub);
        return this;
    }

    public JugadorRequestTestDataBuilder withTarjetasAmarillas(Short tarjetasAmarillas) {
        builder.tarjetasAmarillas(tarjetasAmarillas);
        return this;
    }

    public JugadorRequestTestDataBuilder withTarjetasRojas(Short tarjetasRojas) {
        builder.tarjetasRojas(tarjetasRojas);
        return this;
    }

    public JugadorRequest build() {

        return builder.build();
    }
}
