package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.CategoriaArbitro;
import com.github.javafaker.Faker;

import java.util.LinkedHashSet;
import java.util.Locale;

public class CategoriaArbitroTestDataBuilder {
    private final CategoriaArbitro.CategoriaArbitroBuilder builder;

    public CategoriaArbitroTestDataBuilder(){
        Faker faker = new Faker(new Locale("es-MX"));

        this.builder = CategoriaArbitro.builder()
                .id((short)faker.number().numberBetween(1,5))
                .descripcionCategoria(faker.lorem().word())
                .arbitros(new LinkedHashSet<>());
    }

    public static CategoriaArbitroTestDataBuilder aCategoriaArbitro(){
        return new CategoriaArbitroTestDataBuilder();
    }

    public CategoriaArbitroTestDataBuilder withId(Short id){
        builder.id(id);
        return this;
    }
    public CategoriaArbitroTestDataBuilder wtihDescripcion(String descripcion){
        builder.descripcionCategoria(descripcion);
        return this;
    }

    public CategoriaArbitro build(){
        return builder.build();
    }
}
