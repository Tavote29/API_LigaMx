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
@Table(name = "ESTADIOS")
public class Estadio {
    @Id
    @Column(name = "ID_ESTADIO", nullable = false)
    private Short id;

    @Size(max = 250)
    @NotNull
    @Column(name = "NOMBRE_ESTADIO", nullable = false, length = 250)
    private String nombreEstadio;

    @Size(max = 300)
    @NotNull
    @Column(name = "DIRECCION", nullable = false, length = 300)
    private String direccion;

    @NotNull
    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    private Estado idEstado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CIUDAD", nullable = false)
    private Ciudad idCiudad;

    @OneToMany(mappedBy = "idEstadio")
    private Set<Club> clubs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idEstadio")
    private Set<Partido> partidos = new LinkedHashSet<>();


}