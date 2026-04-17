package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.request.ClubRequest;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

public class ClubRequestTestDataBuilder {

    private final ClubRequest.ClubRequestBuilder builder;

    public ClubRequestTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        this.builder = ClubRequest.builder()
                .nombreClub(faker.company().name())
                .fechaFundacion(faker.date().birthday(50, 100).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .propietario(faker.name().fullName())
                .idEstado((short) faker.number().numberBetween(1, 32))
                .idCiudad((short) faker.number().numberBetween(1, 100))
                .idDt(faker.number().randomNumber())
                .idEstadio((short) faker.number().numberBetween(1, 50));
    }

    public static ClubRequestTestDataBuilder aClubRequest() {
        return new ClubRequestTestDataBuilder();
    }

    public ClubRequestTestDataBuilder withNombreClub(String nombreClub) {
        builder.nombreClub(nombreClub);
        return this;
    }

    public ClubRequestTestDataBuilder withFechaFundacion(LocalDate fechaFundacion) {
        builder.fechaFundacion(fechaFundacion);
        return this;
    }

    public ClubRequestTestDataBuilder withPropietario(String propietario) {
        builder.propietario(propietario);
        return this;
    }

    public ClubRequestTestDataBuilder withIdEstado(Short idEstado) {
        builder.idEstado(idEstado);
        return this;
    }

    public ClubRequestTestDataBuilder withIdCiudad(Short idCiudad) {
        builder.idCiudad(idCiudad);
        return this;
    }

    public ClubRequestTestDataBuilder withIdDt(Long idDt) {
        builder.idDt(idDt);
        return this;
    }

    public ClubRequestTestDataBuilder withIdEstadio(Short idEstadio) {
        builder.idEstadio(idEstadio);
        return this;
    }

    public ClubRequest build() {
        return builder.build();
    }
}
