package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.entitys.Posicion;
import com.fdf.liga_mx.models.enums.Estados;
import com.github.javafaker.Faker;

import java.util.LinkedHashSet;
import java.util.Locale;

public class JugadorTestDataBuilder {

    private final Jugador.JugadorBuilder builder;

    public JugadorTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        Persona persona = PersonaTestDataBuilder.aPersona().build();
        Posicion posicion = PosicionTestDataBuilder.aPosicion().build();
        Club club = ClubTestDataBuilder.aClub().build();

        this.builder = Jugador.builder()
                .id(faker.number().randomNumber())
                .tarjetasAmarillas(null)
                .tarjetasRojas(null)
                .dorsal((short) faker.number().numberBetween(1, 99))
                .idPersona(persona)
                .idPosicion(posicion)
                .idClub(club)
                .status(Estados.ACTIVO.getCodigo())
                .acontecimientosPrimario(new LinkedHashSet<>())
                .acontecimientosSecundario(new LinkedHashSet<>());
    }

    public static JugadorTestDataBuilder aJugador() {
        return new JugadorTestDataBuilder();
    }

    public JugadorTestDataBuilder withId(Long id) {
        builder.id(id);
        return this;
    }

    public JugadorTestDataBuilder withTarjetasAmarillas(Short tarjetasAmarillas) {
        builder.tarjetasAmarillas(tarjetasAmarillas);
        return this;
    }

    public JugadorTestDataBuilder withTarjetasRojas(Short tarjetasRojas) {
        builder.tarjetasRojas(tarjetasRojas);
        return this;
    }

    public JugadorTestDataBuilder withDorsal(Short dorsal) {
        builder.dorsal(dorsal);
        return this;
    }

    public JugadorTestDataBuilder withIdPersona(Persona idPersona) {
        builder.idPersona(idPersona);
        return this;
    }

    public JugadorTestDataBuilder withIdPosicion(Posicion idPosicion) {
        builder.idPosicion(idPosicion);
        return this;
    }

    public JugadorTestDataBuilder withIdClub(Club idClub) {
        builder.idClub(idClub);
        return this;
    }

    public JugadorTestDataBuilder withStatus(Short status) {
        builder.status(status);
        return this;
    }

    public Jugador build() {
        return builder.build();
    }
}
