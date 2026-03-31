package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "JUGADORES")
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUI_JUGADOR", nullable = false)
    private Long id;

    @Column(name = "TARJETAS_AMARILLAS", columnDefinition = "tinyint")
    private Short tarjetasAmarillas;

    @Column(name = "TARJETAS_ROJAS", columnDefinition = "tinyint")
    private Short tarjetasRojas;

    @Column(name = "DORSAL", nullable = false)
    private Short dorsal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_PERSONA", nullable = false)
    private Persona idPersona;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_POSICION", nullable = false)
    private Posicion idPosicion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CLUB", nullable = false)
    private Club idClub;

    @OneToMany(mappedBy = "idJugadorPrimario")
    @ToString.Exclude
    private Set<Acontecimiento> acontecimientosPrimario = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idJugadorSecundario")
    @ToString.Exclude
    private Set<Acontecimiento> acontecimientosSecundario = new LinkedHashSet<>();



}
