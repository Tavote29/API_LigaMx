package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.TiposAcontecimiento;
import com.github.javafaker.Faker;

import java.util.LinkedHashSet;
import java.util.Locale;

public class TiposAcontecimientoTestDataBuilder {
    private final TiposAcontecimiento.TiposAcontecimientoBuilder builder;

    public TiposAcontecimientoTestDataBuilder(){
        Faker faker = new Faker(new Locale("es-MX"));

        this.builder = TiposAcontecimiento.builder()
                .id((short) faker.number().numberBetween(1,11))
                .descripcionTipo(faker.lorem().sentence())
                .acontecimientos(new LinkedHashSet<>())
        ;
    }

    public static TiposAcontecimientoTestDataBuilder aTipoAcontecimiento(){
        return new TiposAcontecimientoTestDataBuilder();
    }
    public TiposAcontecimientoTestDataBuilder withId(Short id){
        builder.id(id);
        return this;
    }
    public TiposAcontecimientoTestDataBuilder withDescripcion(String descripcion){
        builder.descripcionTipo(descripcion);
        return this;
    }
    public TiposAcontecimiento build(){
        return builder.build();
    }
}
