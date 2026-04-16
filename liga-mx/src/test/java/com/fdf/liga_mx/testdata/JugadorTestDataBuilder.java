package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.request.PersonaRequest;
import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Persona;
import com.fdf.liga_mx.models.entitys.Posicion;
import com.fdf.liga_mx.models.entitys.Nacionalidad;
import com.fdf.liga_mx.models.entitys.Status;
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
                .tarjetasAmarillas((short)0)
                .tarjetasRojas((short)0)
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

    public JugadorTestDataBuilder fromRequest(JugadorRequest request) {
        if (request != null) {
            if (request.getDorsal() != null) {
                this.withDorsal(request.getDorsal());
            }
            if (request.getTarjetasAmarillas() != null) {
                this.withTarjetasAmarillas(request.getTarjetasAmarillas());
            }
            if (request.getTarjetasRojas() != null) {
                this.withTarjetasRojas(request.getTarjetasRojas());
            }
            if (request.getId_posicion() != null) {
                Posicion posicion = PosicionTestDataBuilder.aPosicion().withId(request.getId_posicion()).build();
                this.withIdPosicion(posicion);
            }
            if (request.getId_club() != null) {
                Club club = ClubTestDataBuilder.aClub().withId(request.getId_club()).build();
                this.withIdClub(club);
            }

            PersonaRequest personaRequest = request.getPersona();
            if (personaRequest != null) {
                PersonaTestDataBuilder personaBuilder = PersonaTestDataBuilder.aPersona();
                if (personaRequest.getNombre() != null) {
                    personaBuilder.withNombre(personaRequest.getNombre());
                }
                if (personaRequest.getFechaNacimiento() != null) {
                    personaBuilder.withFechaNacimiento(personaRequest.getFechaNacimiento());
                }
                if (personaRequest.getLugarNacimiento() != null) {
                    personaBuilder.withLugarNacimiento(personaRequest.getLugarNacimiento());
                }
                if (personaRequest.getEstatura() != null) {
                    personaBuilder.withEstatura(personaRequest.getEstatura());
                }
                if (personaRequest.getPeso() != null) {
                    personaBuilder.withPeso(personaRequest.getPeso());
                }
                if (personaRequest.getIdNacionalidad() != null) {
                    Nacionalidad nacionalidad = NacionalidadTestDataBuilder.aNacionalidad().withId(personaRequest.getIdNacionalidad()).build();
                    personaBuilder.withIdNacionalidad(nacionalidad);
                }
                if (personaRequest.getIdStatus() != null) {
                    Status status = StatusTestDataBuilder.aStatus().withId(personaRequest.getIdStatus()).build();
                    personaBuilder.withIdStatus(status);
                }
                this.withIdPersona(personaBuilder.build());
            }
        }
        return this;
    }

    public Jugador build() {
        return builder.build();
    }
}
