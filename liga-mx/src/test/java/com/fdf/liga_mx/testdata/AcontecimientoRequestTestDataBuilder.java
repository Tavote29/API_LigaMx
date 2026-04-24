package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.request.AcontecimientoRequest;
import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class AcontecimientoRequestTestDataBuilder {
    private final AcontecimientoRequest.AcontecimientoRequestBuilder builder;

    public AcontecimientoRequestTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));
        UUID uuid = UUID.randomUUID();

        this.builder = AcontecimientoRequest.builder()
                .idTipo((short) faker.number().numberBetween(1,11))
                .minuto(generateAddedTime())
                .idJugadorPrimario(faker.number().randomNumber())
                .idJugadorSecundario(faker.number().randomNumber())
                .idPartido(uuid)
        ;
    }

    public static AcontecimientoRequestTestDataBuilder anAcontecimiento() {
        return new AcontecimientoRequestTestDataBuilder();
    }

    public AcontecimientoRequestTestDataBuilder withTipo(Short tipo){
        builder.idTipo(tipo);
        return this;
    }
    public AcontecimientoRequestTestDataBuilder withMinuto(String minuto){
        builder.minuto(minuto);
        return this;
    }
    public AcontecimientoRequestTestDataBuilder withJugadorPrimario(Long idJugadorPrimario){
        builder.idJugadorPrimario(idJugadorPrimario);
        return this;
    }
    public AcontecimientoRequestTestDataBuilder withJugadorSecundario(Long idJugadorSecundario){
        builder.idJugadorSecundario(idJugadorSecundario);
        return this;
    }
    public AcontecimientoRequestTestDataBuilder withPartido(UUID uuid){
        builder.idPartido(uuid);
        return this;
    }

    public String generateAddedTime(){
        Random random = new Random();
        boolean addedTime = random.nextInt(100) < 20;
        if(addedTime){
            int base = random.nextBoolean() ? 45 : 90;
            int extra = random.nextInt(15) + 1;

            return base + "+" + extra;
        }
        return String.valueOf(random.nextInt(90)+1);
    }

    public AcontecimientoRequest build(){
        return builder.build();
    }
}
