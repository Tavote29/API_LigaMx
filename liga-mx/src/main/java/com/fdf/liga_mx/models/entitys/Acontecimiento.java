package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "ACONTECIMIENTOS")
public class Acontecimiento {
    @Id
    @Column(name = "ID_ACONTECIMEINTO", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_TIPO", nullable = false)
    private TiposAcontecimiento idTipo;

    @Size(max = 10)
    @NotNull
    @Column(name = "MINUTO", nullable = false, length = 10)
    private String minuto;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_JUGADOR_PRIMARIO", nullable = false)
    private Jugador idJugadorPrimario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_JUGADOR_SECUNDARIO")
    private Jugador idJugadorSecundario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PARTIDO", nullable = false)
    private Partido idPartido;


}