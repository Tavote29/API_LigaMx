package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Arbitro;
import com.fdf.liga_mx.models.entitys.CategoriaArbitro;
import com.fdf.liga_mx.models.entitys.Persona;
import com.github.javafaker.Faker;

import java.time.ZoneId;
import java.util.*;

public class ArbitroTestDataBuilder {

    private final Arbitro.ArbitroBuilder builder;

    public ArbitroTestDataBuilder(){
        Faker faker = new Faker(new Locale("es-MX"));

        Persona persona = PersonaTestDataBuilder.aPersona().build();
        CategoriaArbitro categoriaArbitro = CategoriaArbitroTestDataBuilder.aCategoriaArbitro().build();

        Date from = new GregorianCalendar(2020,Calendar.JANUARY,1).getTime();
        Date to = new Date();
        this.builder = Arbitro.builder()
                .id(faker.number().randomNumber())
                .persona(persona)
                .fechaIncorporacion(faker.date().between(from,to)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .idCategoriaArbitro(categoriaArbitro)
                .status((short) 1)
                .partidosArbitoCentral(new LinkedHashSet<>())
                .partidosAsistente1(new LinkedHashSet<>())
                .partidosAsistente2(new LinkedHashSet<>())
                .partidosCuartoArbitro(new LinkedHashSet<>());
    }

    public static ArbitroTestDataBuilder anArbitro(){
        return new ArbitroTestDataBuilder();
    }

    public ArbitroTestDataBuilder withId(Long id){
        builder.id(id);
        return this;
    }
    public ArbitroTestDataBuilder withPersona(Persona persona){
        builder.persona(persona);
        return this;
    }
    public ArbitroTestDataBuilder withStatus(Short status){
        builder.status(status);
        return this;
    }

    public Arbitro build(){
        return builder.build();
    }
}
