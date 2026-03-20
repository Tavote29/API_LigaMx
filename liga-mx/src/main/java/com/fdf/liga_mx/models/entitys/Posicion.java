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

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Set<Jugador> jugadores = new LinkedHashSet<>();


}