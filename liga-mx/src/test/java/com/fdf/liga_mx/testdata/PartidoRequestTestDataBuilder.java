package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.request.PartidoRequest;
import com.fdf.liga_mx.models.entitys.Status;
import com.github.javafaker.Faker;

import java.time.Instant;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class PartidoRequestTestDataBuilder {
    private final PartidoRequest.PartidoRequestBuilder builder;

    public PartidoRequestTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        List<Long> torneos = List.of(1L,5L,6L,7L);
        this.builder = PartidoRequest.builder()
                .idLocal((short) faker.number().numberBetween(1,18))
                .idVisitante((short) faker.number().numberBetween(1,18))
                .idEstadio((short) faker.number().numberBetween(1,17))
                .idArbitroCentral(faker.number().randomNumber())
                .idArbitroAsistente1(faker.number().randomNumber())
                .idArbitroAsistente2(faker.number().randomNumber())
                .idCuartoArbitro(faker.number().randomNumber())
                .fecha(Instant.now().plusSeconds(3600))
                .idStatus((short) 1)
                .idTorneo(torneos.get((int) faker.random().nextLong(torneos.size())));
    }

    public static PartidoRequestTestDataBuilder aPartidoRequest(){
        return  new PartidoRequestTestDataBuilder();
    }

    public PartidoRequestTestDataBuilder withTorneo(Long idTorneo){
        builder.idTorneo(idTorneo);
        return this;
    }

    public PartidoRequestTestDataBuilder withFecha(Instant fecha) {
        builder.fecha(fecha);
        return this;

    }

    public PartidoRequest build(){
        return builder.build();
    }
}
