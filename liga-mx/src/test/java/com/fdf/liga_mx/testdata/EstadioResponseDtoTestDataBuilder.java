package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.dtos.response.CiudadResponseDto;
import com.fdf.liga_mx.models.dtos.response.EstadioResponseDto;
import com.fdf.liga_mx.models.dtos.response.EstadoResponseDto;
import com.fdf.liga_mx.models.entitys.Estadio;
import com.github.javafaker.Faker;

import java.util.Locale;

public class EstadioResponseDtoTestDataBuilder {

    private final EstadioResponseDto.EstadioResponseDtoBuilder builder;

    public EstadioResponseDtoTestDataBuilder() {
        Faker faker = new Faker(new Locale("es-MX"));

        EstadoResponseDto estado = EstadoResponseDto.builder()
                .id((short) faker.number().numberBetween(1, 32))
                .nombreEstado(faker.address().state())
                .build();

        CiudadResponseDto ciudad = CiudadResponseDto.builder()
                .id((short) faker.number().numberBetween(1, 100))
                .nombreCiudad(faker.address().city())
                .idEstado(estado)
                .build();

        this.builder = EstadioResponseDto.builder()
                .id((short) faker.number().numberBetween(1, 100))
                .nombreEstadio(faker.company().name() + " Stadium")
                .direccion(faker.address().streetAddress())
                .capacidad(faker.number().numberBetween(10000, 100000))
                .idEstado(estado)
                .idCiudad(ciudad);
    }

    public static EstadioResponseDtoTestDataBuilder anEstadioResponseDto() {
        return new EstadioResponseDtoTestDataBuilder();
    }

    public EstadioResponseDtoTestDataBuilder withId(Short id) {
        builder.id(id);
        return this;
    }

    public EstadioResponseDtoTestDataBuilder withNombreEstadio(String nombreEstadio) {
        builder.nombreEstadio(nombreEstadio);
        return this;
    }

    public EstadioResponseDtoTestDataBuilder withDireccion(String direccion) {
        builder.direccion(direccion);
        return this;
    }

    public EstadioResponseDtoTestDataBuilder withCapacidad(Integer capacidad) {
        builder.capacidad(capacidad);
        return this;
    }

    public EstadioResponseDtoTestDataBuilder withIdEstado(EstadoResponseDto idEstado) {
        builder.idEstado(idEstado);
        return this;
    }

    public EstadioResponseDtoTestDataBuilder withIdCiudad(CiudadResponseDto idCiudad) {
        builder.idCiudad(idCiudad);
        return this;
    }

    public EstadioResponseDtoTestDataBuilder fromEntity(Estadio estadio) {
        if (estadio != null) {
            if (estadio.getId() != null) {
                this.withId(estadio.getId());
            }
            if (estadio.getNombreEstadio() != null) {
                this.withNombreEstadio(estadio.getNombreEstadio());
            }
            if (estadio.getDireccion() != null) {
                this.withDireccion(estadio.getDireccion());
            }
            if (estadio.getCapacidad() != null) {
                this.withCapacidad(estadio.getCapacidad());
            }
            
            if (estadio.getIdEstado() != null) {
                EstadoResponseDto estadoDto = EstadoResponseDto.builder()
                        .id(estadio.getIdEstado().getId())
                        .nombreEstado(estadio.getIdEstado().getNombreEstado())
                        .build();
                this.withIdEstado(estadoDto);
            }
            
            if (estadio.getIdCiudad() != null) {
                EstadoResponseDto estadoForCiudad = null;
                if(estadio.getIdCiudad().getIdEstado() != null){
                     estadoForCiudad = EstadoResponseDto.builder()
                            .id(estadio.getIdCiudad().getIdEstado().getId())
                            .nombreEstado(estadio.getIdCiudad().getIdEstado().getNombreEstado())
                            .build();
                }
                
                CiudadResponseDto ciudadDto = CiudadResponseDto.builder()
                        .id(estadio.getIdCiudad().getId())
                        .nombreCiudad(estadio.getIdCiudad().getNombreCiudad())
                        .idEstado(estadoForCiudad)
                        .build();
                this.withIdCiudad(ciudadDto);
            }
        }
        return this;
    }

    public EstadioResponseDto build() {
        return builder.build();
    }
}
