package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Ciudad;
import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.entitys.DT;
import com.fdf.liga_mx.models.entitys.Estadio;
import com.fdf.liga_mx.models.entitys.Estado;
import com.github.javafaker.Faker;

import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.Locale;

public class ClubTestDataBuilder {

    private final Club.ClubBuilder builder;

    public ClubTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));
        
        Estado estado = EstadoTestDataBuilder.anEstado().build();
        Ciudad ciudad = CiudadTestDataBuilder.aCiudad().withIdEstado(estado).build();
        Estadio estadio = EstadioTestDataBuilder.anEstadio().withIdEstado(estado).withIdCiudad(ciudad).build();

        this.builder = Club.builder()
                .id((short) faker.number().numberBetween(1, 100))
                .nombreClub(faker.team().name())
                .fechaFundacion(faker.date().birthday(10, 100).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .propietario(faker.name().fullName())
                .idEstado(estado)
                .idCiudad(ciudad)
                .idDt(null)
                .imageUrl(faker.internet().url())
                .idEstadio(estadio)
                .status((short) 1)
                .jugadores(new LinkedHashSet<>())
                .partidosLocal(new LinkedHashSet<>())
                .partidosVisitante(new LinkedHashSet<>());
    }

    public static ClubTestDataBuilder aClub() {
        return new ClubTestDataBuilder();
    }

    public ClubTestDataBuilder withId(Short id) {
        builder.id(id);
        return this;
    }

    public ClubTestDataBuilder withNombreClub(String nombreClub) {
        builder.nombreClub(nombreClub);
        return this;
    }

    public ClubTestDataBuilder withFechaFundacion(java.time.LocalDate fechaFundacion) {
        builder.fechaFundacion(fechaFundacion);
        return this;
    }

    public ClubTestDataBuilder withPropietario(String propietario) {
        builder.propietario(propietario);
        return this;
    }

    public ClubTestDataBuilder withIdEstado(Estado idEstado) {
        builder.idEstado(idEstado);
        return this;
    }

    public ClubTestDataBuilder withIdCiudad(Ciudad idCiudad) {
        builder.idCiudad(idCiudad);
        return this;
    }

    public ClubTestDataBuilder withIdDt(DT idDt) {
        builder.idDt(idDt);
        return this;
    }

    public ClubTestDataBuilder withImageUrl(String imageUrl) {
        builder.imageUrl(imageUrl);
        return this;
    }

    public ClubTestDataBuilder withIdEstadio(Estadio idEstadio) {
        builder.idEstadio(idEstadio);
        return this;
    }

    public ClubTestDataBuilder withStatus(Short status) {
        builder.status(status);
        return this;
    }

    public Club build() {
        return builder.build();
    }
}
