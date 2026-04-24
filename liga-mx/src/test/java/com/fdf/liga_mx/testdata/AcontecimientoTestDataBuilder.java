package com.fdf.liga_mx.testdata;

import com.fdf.liga_mx.models.entitys.Acontecimiento;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Partido;
import com.fdf.liga_mx.models.entitys.TiposAcontecimiento;

import java.util.UUID;

public class AcontecimientoTestDataBuilder {
    private final Acontecimiento.AcontecimientoBuilder builder;

    public AcontecimientoTestDataBuilder(){
        UUID uuid = UUID.randomUUID();

        TiposAcontecimiento tipo = TiposAcontecimientoTestDataBuilder.aTipoAcontecimiento().build();
        Jugador jugador1 = JugadorTestDataBuilder.aJugador().build();
        Jugador jugador2 = JugadorTestDataBuilder.aJugador().build();
        Partido partido = PartidoTestDataBuilder.aPartido().build();
        AcontecimientoRequestTestDataBuilder request = new AcontecimientoRequestTestDataBuilder();

        this.builder = Acontecimiento.builder()
                .id(uuid)
                .idTipo(tipo)
                .idJugadorPrimario(jugador1)
                .idJugadorSecundario(jugador2)
                .minuto(request.generateAddedTime())
                .idPartido(partido)
        ;
    }

    public static AcontecimientoTestDataBuilder anAcontecimiento(){
        return new AcontecimientoTestDataBuilder();
    }

    public AcontecimientoTestDataBuilder withId(UUID uuid){
        builder.id(uuid);
        return this;
    }

    public AcontecimientoTestDataBuilder withTipo(TiposAcontecimiento tipo){
        builder.idTipo(tipo);
        return this;
    }
    public AcontecimientoTestDataBuilder withJugador1(Jugador jugador){
        builder.idJugadorPrimario(jugador);
        return this;
    }
    public AcontecimientoTestDataBuilder withJugador2(Jugador jugador){
        builder.idJugadorPrimario(jugador);
        return this;
    }
    public AcontecimientoTestDataBuilder withMinuto(String minuto){
        builder.minuto(minuto);
        return this;
    }
    public AcontecimientoTestDataBuilder withPartido(Partido partido){
        builder.idPartido(partido);
        return this;
    }
    public Acontecimiento build(){
        return builder.build();
    }
}
