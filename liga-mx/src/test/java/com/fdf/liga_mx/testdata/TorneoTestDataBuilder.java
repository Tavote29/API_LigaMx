package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Torneo;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TorneoTestDataBuilder {
    private  final Torneo.TorneoBuilder builder;

    public TorneoTestDataBuilder(){
        Faker faker = new Faker(new Locale("es-MX"));

        Date inicioDate = faker.date().past(10, TimeUnit.DAYS);
        Date finDate = faker.date().future(10, TimeUnit.DAYS);

        this.builder = Torneo.builder()
                .id(faker.number().randomNumber())
                .nombreTorneo(faker.lorem().word())
                .fechaInicio(LocalDate.ofInstant(inicioDate.toInstant(), ZoneId.systemDefault()))
                .fechaFin(LocalDate.ofInstant(finDate.toInstant(), ZoneId.systemDefault()))
                .status((short)1)
                .partidos(new LinkedHashSet<>());
    }

    public static TorneoTestDataBuilder aTorneo(){
        return new TorneoTestDataBuilder();
    }
    public TorneoTestDataBuilder withId(Long id){
        builder.id(id);
        return this;
    }
    public TorneoTestDataBuilder withStatus(Short status){
        builder.status(status);
        return this;
    }
    public Torneo build(){
        return builder.build();
    }
}
