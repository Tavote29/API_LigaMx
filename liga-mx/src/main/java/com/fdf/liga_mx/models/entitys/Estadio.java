package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "ESTADIOS")
public class Estadio {
    @Id
    @Column(name = "ID_ESTADIO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    private Estado idEstado;

    @NotNull
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CIUDAD", nullable = false)
    private Ciudad idCiudad;

    @ToString.Exclude
    @OneToMany(mappedBy = "idEstadio")
    private Set<Club> clubs = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "idEstadio")
    private Set<Partido> partidos = new LinkedHashSet<>();


}