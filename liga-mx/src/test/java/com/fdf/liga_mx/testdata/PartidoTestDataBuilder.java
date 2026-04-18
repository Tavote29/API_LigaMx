package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.*;
import com.github.javafaker.Faker;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.UUID;

public class PartidoTestDataBuilder {

    private final Partido.PartidoBuilder builder;

    public PartidoTestDataBuilder(){
        Faker faker = new Faker(new Locale("es-MX"));

        Club local = ClubTestDataBuilder.aClub().build();
        Club visitante = ClubTestDataBuilder.aClub().build();
        Estadio estadio = EstadioTestDataBuilder.anEstadio().build();
        Arbitro arbitroCentral = ArbitroTestDataBuilder.anArbitro().build();
        Arbitro arbitroAsistente1 = ArbitroTestDataBuilder.anArbitro().build();
        Arbitro arbitroAsistente2 = ArbitroTestDataBuilder.anArbitro().build();
        Arbitro cuartoArbitro = ArbitroTestDataBuilder.anArbitro().build();
        Status status = StatusTestDataBuilder.aStatus().build();
        Torneo torneo = TorneoTestDataBuilder.aTorneo().build();

        this.builder = Partido.builder()
                .id(UUID.randomUUID())
                .idLocal(local)
                .idVisitante(visitante)
                .idEstadio(estadio)
                .golesLocal((short)0)
                .golesVisitante((short)0)
                .idArbitroCentral(arbitroCentral)
                .idArbitroAsistente1(arbitroAsistente1)
                .idArbitroAsistente2(arbitroAsistente2)
                .idCuartoArbitro(cuartoArbitro)
                .fecha(Instant.now().plusSeconds(3600))
                .idStatus(status)
                .acontecimientos(new LinkedHashSet<>())
                .idTorneo(torneo);

    }
    public static PartidoTestDataBuilder aPartido(){
        return new PartidoTestDataBuilder();
    }
    public PartidoTestDataBuilder withId(UUID uuid){
        builder.id(uuid);
        return this;
    }
    public Partido build(){
        return builder.build();
    }

}
