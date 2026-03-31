package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "POSICIONES")
public class Posicion {
    @Id
    @Column(name = "ID_POSICION", nullable = false)
    private Short id;

    @Size(max = 50)
    @NotNull
    @Column(name = "DESCRIPCION_POSICION", nullable = false, length = 50)
    private String descripcionPosicion;

    @OneToMany(mappedBy = "idPosicion")
    @ToString.Exclude
    private Set<Jugador> jugadores = new LinkedHashSet<>();


}